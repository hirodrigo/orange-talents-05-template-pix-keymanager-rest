package br.com.zup.pix.consulta

import br.com.zup.ConsultaChavePixRequest.FiltroPorPixId
import br.com.zup.ConsultaChavePixRequest.newBuilder
import br.com.zup.KeyManagerConsultaServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import java.util.*


@Validated
@Controller("/api/v1/clientes/{clienteId}")
class ConsultaChavePixController(
    private val consultaChavePixClient: KeyManagerConsultaServiceGrpc.KeyManagerConsultaServiceBlockingStub
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Get("/pix/{pixId}")
    fun registra(
        @PathVariable clienteId: UUID,
        @PathVariable pixId: UUID
    ): HttpResponse<Any> {

        val grpcResponse = consultaChavePixClient.consulta(
            newBuilder()
                .setPixId(
                    FiltroPorPixId.newBuilder()
                        .setClienteId(clienteId.toString())
                        .setPixId(pixId.toString())
                        .build()
                )
                .build()
        )
        logger.info("Chave $pixId consultada com sucesso!")
        return HttpResponse.ok(ConsultaChavePixResponse(grpcResponse))
    }
}