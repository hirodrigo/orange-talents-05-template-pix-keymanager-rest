package br.com.zup.pix

import br.com.zup.TipoConta

enum class TipoContaRequest {
    CONTA_CORRENTE,
    CONTA_POUPANCA;

    fun grpcType(): TipoConta {
        return when (this) {
            CONTA_CORRENTE -> TipoConta.CONTA_CORRENTE
            CONTA_POUPANCA -> TipoConta.CONTA_POUPANCA
        }
    }
}