Vault
=====

The vault contains data extracted from the ledger that is considered relevant to the node’s owner, stored in a relational model
that can be easily queried and worked with. The vault supports the management of data in both authoritative ("on-ledger") form
and, where appropriate, shadow ("off-ledger") form:

* "On-ledger" data refers to Distributed Ledger State (cash, deals, trades) to which a firm is participant.
* "Off-ledger" data refers to a firm's internal reference, static and systems data.

The vault keeps track of both unconsumed and consumed state:

 * unconsumed (or unspent state) represents fungible state available for spending and linear state available for transfer to another party.
 * consumed (or spent state) represents ledger immutable state for the purpose of transaction reporting, audit and archival, including the ability to perform joins with app-private data (like customer notes)

There is also a facility for attaching descriptive textual notes against any transaction stored in the vault.

Like with a cryptocurrency wallet, the Corda vault understands how to create transactions that send value to someone else
by combining asset states and possibly adding a change output that makes the values balance. This process is usually referred to as ‘coin selection’.
Vault spending ensures that transactions respect the fungibility rules in order to ensure that the issuer and reference data is preserved as the assets pass from hand to hand.

The following diagram illustrates the breakdown of the vault into sub-system components:

.. image:: resources/vault.png

Note the following:

* the vault "On Ledger" store tracks unconsumed state and is updated internally by the node upon recording of a transaction on the ledger (following succesful smart contract verification and signature by all participants)
* the vault "Off Ledger" store refers to additional data added by the node owner subsequent to transaction recording
* the vault performs transaction processing
* Vault extensions represent additional custom plugin code a developer may write to query specific custom contract state attributes.
* Customer "Off Ledger" (private store) represents internal organisational data that may be joined with the vault data to perform additional reporting or processing
* a vault API is exposed to developers using standard Corda RPC and CorDapp plugin mechanisms
* additionally the vault database schemas are directly accessible via JDBC for customer joins and queries

Section 8 of the `Technical white paper`_ describes features of the vault yet to be implemented including private key managament,
soft state locking, state splitting and merging, asset re-issuance and node event scheduling.

.. _`Technical white paper`: _static/corda-technical-whitepaper.pdf

