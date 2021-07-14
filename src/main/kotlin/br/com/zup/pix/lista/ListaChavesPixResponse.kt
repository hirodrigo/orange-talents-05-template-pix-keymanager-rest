package br.com.zup.pix.lista

import br.com.zup.ListaChavesPixResponse
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class ListaChavesPixResponse(grpcResponse: ListaChavesPixResponse) {

    val clienteId = grpcResponse.clienteId
    val chaves = grpcResponse.chavesList.map { chave ->
        mapOf(
            Pair("id", chave.pixId),
            Pair("tipoChave", chave.tipoChave),
            Pair("chave", chave.chave),
            Pair("tipoConta", chave.tipoConta),
            Pair("criadaEm", chave.criadaEm.let {
                LocalDateTime.ofInstant(Instant.ofEpochSecond(it.seconds, it.nanos.toLong()), ZoneOffset.UTC)
            })
        )
    }
}
