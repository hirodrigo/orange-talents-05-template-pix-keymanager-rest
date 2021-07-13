package br.com.zup.pix.shared.grpc

import br.com.zup.KeyManagerConsultaServiceGrpc
import br.com.zup.KeyManagerListaServiceGrpc
import br.com.zup.KeyManagerRegistraServiceGrpc
import br.com.zup.KeyManagerRemoveServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class KeyManagerGrpcFactory(
    @GrpcChannel("keyManager") val channel: ManagedChannel
) {

    @Singleton
    fun registraChave() = KeyManagerRegistraServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun removeChave() = KeyManagerRemoveServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun consultaChave() = KeyManagerConsultaServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun listaChaves() = KeyManagerListaServiceGrpc.newBlockingStub(channel)

}