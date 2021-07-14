package br.com.zup.pix.lista

import br.com.zup.KeyManagerListaServiceGrpc
import br.com.zup.ListaChavesPixRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import java.util.*


@Validated
@Controller("/api/v1/clientes/{clienteId}")
class ListaChavesPixController(
    private val listaChavesPixClient: KeyManagerListaServiceGrpc.KeyManagerListaServiceBlockingStub
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Get("/pix/")
    fun registra(
        @PathVariable clienteId: UUID
    ): HttpResponse<Any> {

        val grpcResponse = listaChavesPixClient.lista(
            ListaChavesPixRequest.newBuilder()
                .setClienteId(clienteId.toString())
                .build()
        )
        logger.info("Chaves do cliente $clienteId consultadas com sucesso!")
        return HttpResponse.ok(ListaChavesPixResponse(grpcResponse))
    }
}