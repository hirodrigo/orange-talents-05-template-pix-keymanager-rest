package br.com.zup.pix.registra

import br.com.zup.RegistraChavePixRequest
import br.com.zup.TipoChave.UNKNOWN_TIPO_CHAVE
import br.com.zup.TipoConta.UNKNOWN_TIPO_CONTA
import br.com.zup.pix.TipoChaveRequest
import br.com.zup.pix.TipoContaRequest
import br.com.zup.pix.shared.validation.ValidPixKey
import io.micronaut.core.annotation.Introspected
import java.util.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Introspected
@ValidPixKey
data class NovaChavePixRequest(
    @field:NotNull val tipoConta: TipoContaRequest?,
    @field:NotNull val tipoChave: TipoChaveRequest?,
    @field:Size(max = 77) val chave: String?
) {

    fun paraModeloGrpc(clienteId: UUID): RegistraChavePixRequest {
        return RegistraChavePixRequest.newBuilder()
            .setClienteId(clienteId.toString())
            .setTipoConta(this.tipoConta?.grpcType() ?: UNKNOWN_TIPO_CONTA)
            .setTipoChave(this.tipoChave?.grpcType() ?: UNKNOWN_TIPO_CHAVE)
            .setChave(chave ?: "")
            .build()
    }

}
