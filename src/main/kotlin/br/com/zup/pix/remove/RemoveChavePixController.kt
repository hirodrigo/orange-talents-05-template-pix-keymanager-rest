package br.com.zup.pix.remove

import br.com.zup.KeyManagerRemoveServiceGrpc
import br.com.zup.RemoveChavePixRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.PathVariable
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import java.util.*


@Validated
@Controller("/api/v1/clientes/{clienteId}")
class RemoveChavePixController(
    private val removeChavePixClient: KeyManagerRemoveServiceGrpc.KeyManagerRemoveServiceBlockingStub
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Delete("/pix/{pixId}")
    fun registra(
        @PathVariable clienteId: UUID,
        @PathVariable pixId: UUID
    ): HttpResponse<Any> {

        val grpcResponse = removeChavePixClient.remove(
            RemoveChavePixRequest.newBuilder()
                .setClienteId(clienteId.toString())
                .setPixId(pixId.toString())
                .build()
        )

        logger.info("Chave $pixId removida com sucesso!")
        return HttpResponse.ok()
    }
}