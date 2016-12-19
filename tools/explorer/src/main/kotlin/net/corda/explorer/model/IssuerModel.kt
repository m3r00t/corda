package net.corda.explorer.model

import javafx.collections.ObservableList
import net.corda.client.model.NetworkIdentityModel
import net.corda.client.model.observableList
import net.corda.client.model.observableValue
import net.corda.core.contracts.currency
import net.corda.core.node.NodeInfo
import tornadofx.observable
import java.util.*

val ISSUER_SERVICE_TYPE = Regex("corda.issuer.(USD|GBP|CHF)")

class IssuerModel {
    private val networkIdentities by observableList(NetworkIdentityModel::networkIdentities)
    private val myIdentity by observableValue(NetworkIdentityModel::myIdentity)

    val issuers: ObservableList<NodeInfo> = networkIdentities.filtered { it.advertisedServices.any { it.info.type.id.matches(ISSUER_SERVICE_TYPE) } }

    fun currencyTypes() : ObservableList<Currency> {
        issuerCurrency()?.let {
             return (listOf(it)).observable()
        } ?: return ReportingCurrencyModel().supportedCurrencies
    }

    fun transactionTypes() : ObservableList<CashTransaction> {
        if (isIssuerNode())
            return CashTransaction.values().asList().observable()
        else
            return listOf(CashTransaction.Pay).observable()
    }

    fun isIssuerNode(): Boolean {
        myIdentity.value?.let { myIdentity ->
            if (myIdentity.advertisedServices.any { it.info.type.id.matches(ISSUER_SERVICE_TYPE) }) {
                return true
            }
        }
        return false
    }

    fun issuerCurrency(): Currency? {
        myIdentity.value?.let { myIdentity ->
            if (isIssuerNode()) {
                val issuer = myIdentity.advertisedServices.first { it.info.type.id.matches(ISSUER_SERVICE_TYPE) }
                return currency(issuer.info.type.id.substringAfterLast("."))
            }
        }
        return null
    }
}