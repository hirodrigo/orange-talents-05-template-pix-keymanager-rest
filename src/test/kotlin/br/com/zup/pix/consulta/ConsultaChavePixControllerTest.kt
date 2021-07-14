package br.com.zup.pix.consulta

import br.com.zup.ConsultaChavePixResponse
import br.com.zup.KeyManagerConsultaServiceGrpc
import br.com.zup.TipoChave
import br.com.zup.TipoConta
import br.com.zup.pix.shared.grpc.KeyManagerGrpcFactory
import com.google.protobuf.Timestamp
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.Mockito
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class ConsultaChavePixControllerTest() {

    @field:Inject
    lateinit var consultaStub: KeyManagerConsultaServiceGrpc.KeyManagerConsultaServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    companion object {
        val clienteId = UUID.randomUUID().toString()
        val pixId = UUID.randomUUID().toString()
    }

    @Test
    internal fun `deve retornar detalhes de uma chave pix existente`() {

        BDDMockito.given(consultaStub.consulta(Mockito.any())).willReturn(grpcResponse())

        val request = HttpRequest.GET<Any>("/api/v1/clientes/$clienteId/pix/$pixId")
        val response = client.toBlocking().exchange(request, Any::class.java)

        assertEquals(HttpStatus.OK, response.status)
        assertNotNull(response.body())
    }

    fun grpcResponse(): ConsultaChavePixResponse {
        return ConsultaChavePixResponse.newBuilder()
            .setClienteId(clienteId)
            .setPixId(pixId)
            .setChave(
                ConsultaChavePixResponse.ChavePix.newBuilder()
                    .setTipo(TipoChave.EMAIL)
                    .setChave("teste@email.com")
                    .setConta(
                        ConsultaChavePixResponse.ChavePix.ContaInfo.newBuilder()
                            .setTipo(TipoConta.CONTA_POUPANCA)
                            .setInstituicao("123")
                            .setNomeDoTitular("Nome Titular Teste")
                            .setCpfDoTitular("78632048015")
                            .setAgencia("123")
                            .setNumeroDaConta("123")
                    )
                    .setCriadaEm(
                        LocalDateTime.now().atZone(ZoneId.of("UTC")).toInstant().let {
                            Timestamp.newBuilder()
                                .setSeconds(it.epochSecond)
                                .setNanos(it.nano)
                                .build()
                        }
                    )
            )
            .build()
    }

    @Factory
    @Replaces(factory = KeyManagerGrpcFactory::class)
    internal class MockitoStubFactory {

        @Singleton
        fun stubMock() = Mockito.mock(KeyManagerConsultaServiceGrpc.KeyManagerConsultaServiceBlockingStub::class.java)
    }
}