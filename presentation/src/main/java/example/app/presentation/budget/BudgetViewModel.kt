package example.app.presentation.budget

import dagger.hilt.android.lifecycle.HiltViewModel
import example.app.base.ui.BaseViewModel
import example.app.base.ui.UIState
import example.app.di.qualifiers.IODispatcher
import example.app.model.BudgetDomainModel
import example.app.model.CategoryDomainModel
import example.app.usecase.GetBudgetListUseCase
import example.app.usecase.GetThisMonthTransactionsUseCase
import example.app.usecase.InsertBudgetUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val getBudgetListUseCase: GetBudgetListUseCase,
    private val insertBudgetUseCase : InsertBudgetUseCase,
    private val getThisMonthTransactionsUseCase: GetThisMonthTransactionsUseCase,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher,
    ) : BaseViewModel<BudgetState, BudgetEvents>(coroutineDispatcher) {

    override fun createInitialState(): BudgetState = BudgetState()

    override fun handleEvent(uiEvent: BudgetEvents) {
        when(uiEvent){
            BudgetEvents.LoadBudgetData -> loadBudgetData()
            is BudgetEvents.UpdateBudget -> updateBudget(
                name = uiEvent.name,
                limit = uiEvent.limit,
                isExpense = uiEvent.isExpense,
            )

            is BudgetEvents.UpdateCategory -> {
                setState {
                    copy(
                        selectedCategory = uiEvent.budget
                    )
                }
            }
        }
    }

    private fun loadBudgetData() {
        launchCoroutineScope {
            combine(
                getBudgetListUseCase(),
                getThisMonthTransactionsUseCase()
            ) { budgets, transactions ->

                budgets.map { budget ->
                    val spent = transactions
                        .filter { it.category?.name == budget.name.name }
                        .sumOf {
                                txn ->
                            txn.amount?.toBigDecimalOrNull() ?: BigDecimal.ZERO
                        }

                    BudgetUIModel(
                        name = budget.name.name,
                        budget = budget.limit,
                        spent = spent,
                        currency = "EGP",
                        isExpense = budget.isExpense
                    )
                }
            }.onStart {
                setState {
                    copy(
                        budgetState = UIState.Loading
                    )
                }
            }.catch {
                setState {
                    copy(
                        budgetState = UIState.Error(it.message.orEmpty())
                    )
                }
            }.collect {
                setState {
                    copy(
                        budgetState = UIState.Success(it)
                    )
                }
            }
        }
    }

    private fun updateBudget(name: String, limit: Float, isExpense: Boolean) {
        launchCoroutineScope {
            val request = BudgetDomainModel(name = CategoryDomainModel.valueOf(name) , limit = limit, isExpense = isExpense)
            insertBudgetUseCase.invoke(request)
                .onStart {
                    setState {
                        copy(
                            budgetState = UIState.Loading
                        )
                    }
                }.catch {
                    setState {
                        copy(
                            budgetState = UIState.Error(it.message.orEmpty())
                        )
                    }
                }.collectLatest {
                }
        }
    }

}
