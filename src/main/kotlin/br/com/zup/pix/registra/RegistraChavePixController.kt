package br.com.zup.pix.registra

import br.com.zup.KeyManagerRegistraServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import java.util.*
import javax.validation.Valid


@Validated
@Controller("/api/v1/clientes/{clienteId}")
class RegistraChavePixController(
    private val registraChavePixClient: KeyManagerRegistraServiceGrpc.KeyManagerRegistraServiceBlockingStub
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Post("/pix")
    fun registra(
        @PathVariable clienteId: UUID,
        @Valid @Body request: NovaChavePixRequest
    ): HttpResponse<Any> {

        val grpcResponse = registraChavePixClient.cadastrar(request.paraModeloGrpc(clienteId))

        return HttpResponse.created(location(clienteId, grpcResponse.pixId))
    }

    private fun location(clienteId: UUID, pixId: String) =
        HttpResponse.uri("/api/v1/clientes/${clienteId}/pix/${pixId}")


}