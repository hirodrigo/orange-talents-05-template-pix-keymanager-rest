package br.com.zup.pix.lista

import br.com.zup.KeyManagerListaServiceGrpc
import br.com.zup.ListaChavesPixResponse
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
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.Mockito
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class ListaChavesPixControllerTest {

    @field:Inject
    lateinit var listaStub: KeyManagerListaServiceGrpc.KeyManagerListaServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    companion object {
        val clienteId = UUID.randomUUID().toString()
    }

    @Test
    internal fun `deve retornar todas as chaves de um cliente existente`() {

        BDDMockito.given(listaStub.lista(Mockito.any())).willReturn(grpcResponse())

        val request =
            HttpRequest.GET<Any>("/api/v1/clientes/$clienteId/pix/")
        val response = client.toBlocking().exchange(request, Map::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.status)
        Assertions.assertNotNull(response.body())
    }

    fun grpcResponse(): ListaChavesPixResponse {

        return ListaChavesPixResponse.newBuilder()
            .setClienteId(clienteId)
            .addAllChaves(
                listOf(
                    ListaChavesPixResponse.ChavePix.newBuilder()
                        .setPixId(UUID.randomUUID().toString())
                        .setTipoChave(TipoChave.EMAIL)
                        .setChave("email@teste.com")
                        .setTipoConta(TipoConta.CONTA_CORRENTE)
                        .setCriadaEm(
                            LocalDateTime.now().atZone(ZoneId.of("UTC")).toInstant().let {
                                Timestamp.newBuilder()
                                    .setSeconds(it.epochSecond)
                                    .setNanos(it.nano)
                                    .build()
                            }
                        )
                        .build(),
                    ListaChavesPixResponse.ChavePix.newBuilder()
                        .setPixId(UUID.randomUUID().toString())
                        .setTipoChave(TipoChave.CPF)
                        .setChave("11305589084")
                        .setTipoConta(TipoConta.CONTA_POUPANCA)
                        .setCriadaEm(
                            LocalDateTime.now().minusDays(3).atZone(ZoneId.of("UTC")).toInstant().let {
                                Timestamp.newBuilder()
                                    .setSeconds(it.epochSecond)
                                    .setNanos(it.nano)
                                    .build()
                            }
                        )
                        .build()
                )
            )
            .build()
    }

    @Factory
    @Replaces(factory = KeyManagerGrpcFactory::class)
    internal class MockitoStubFactory {

        @Singleton
        fun stubMock() = Mockito.mock(KeyManagerListaServiceGrpc.KeyManagerListaServiceBlockingStub::class.java)
    }

}