package br.com.zup.pix

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*

internal class TipoChaveRequestTest {

    @Nested
    inner class ChaveAleatoriaTest {
        @Test
        fun `deve ser valido quando chave aleatoria for nula ou vazia`() {
            with(TipoChaveRequest.ALEATORIA) {
                assertTrue(valida(null))
                assertTrue(valida(""))
            }
        }

        @Test
        fun `nao deve ser valido quando chave aleatoria possuir um valor`() {
            with(TipoChaveRequest.ALEATORIA) {
                assertFalse(valida(UUID.randomUUID().toString()))
            }
        }
    }

    @Nested
    inner class CpfTest {
        @Test
        fun `deve ser valido quando cpf tiver numero valido`() {
            with(TipoChaveRequest.CPF) {
                assertTrue(valida("85109841071"))
            }
        }

        @Test
        fun `nao deve ser valido quando cpf tiver numero invalido`() {
            with(TipoChaveRequest.CPF) {
                assertFalse(valida("85109841072"))
                assertFalse(valida("85109841071a"))
            }
        }

        @Test
        fun `nao deve ser valido quando cpf tiver numero nulo ou vazio`() {
            with(TipoChaveRequest.CPF) {
                assertFalse(valida(null))
                assertFalse(valida(""))
            }
        }
    }

    @Nested
    inner class CelularTest {

        @Test
        fun `deve ser valido quando celular for numero valido`() {
            with(TipoChaveRequest.CELULAR) {
                assertTrue(valida("+5511987651234"))
            }
        }

        @Test
        fun `nao deve ser valido quando celular for numero invalido`() {
            with(TipoChaveRequest.CELULAR) {
                assertFalse(valida("11987651234"))
                assertFalse(valida("119876b51234"))
            }
        }

        @Test
        fun `nao deve ser valido quando celular tiver numero nulo ou vazio`() {
            with(TipoChaveRequest.CELULAR) {
                assertFalse(valida(null))
                assertFalse(valida(""))
            }
        }
    }

    @Nested
    inner class EmailTest {

        @Test
        fun `deve ser valido quando email for endereco valido`() {
            with(TipoChaveRequest.EMAIL) {
                assertTrue(valida("email@teste.com"))
            }
        }

        @Test
        fun `nao deve ser valido quando email for endereco invalido`() {
            with(TipoChaveRequest.EMAIL) {
                assertFalse(valida("emailteste.com"))
                assertFalse(valida("email@teste.com."))
            }
        }

        @Test
        fun `nao deve ser valido quando email for nulo ou vazio`() {
            with(TipoChaveRequest.EMAIL) {
                assertFalse(valida(null))
                assertFalse(valida(""))
            }
        }
    }


}