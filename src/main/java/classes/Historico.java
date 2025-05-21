package classes;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import java.time.LocalDate;

@Entity
public class Historico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_operacao")
    private TipoOperacao tipoOperacao;

    @Column(name = "data_operacao")
    private LocalDate dataOperacao;

    @Column(name = "usuario")
    private String nomeUsuario;

    @Column(name = "nome_peca")
    private String nomePeca;

    @Column(name = "numero_serie")
    private String numeroSerie;

    @Column(name = "marca")
    private String marca;
    public Historico() {
    }

    public Historico(TipoOperacao tipoOperacao, LocalDate dataOperacao, String nomeUsuario, String nomePeca, String numeroSerie, String marca) {
        this.tipoOperacao = tipoOperacao;
        this.dataOperacao = dataOperacao;
        this.nomeUsuario = nomeUsuario;
        this.nomePeca = nomePeca;
        this.numeroSerie = numeroSerie;
        this.marca = marca;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoOperacao getTipoOperacao() {
        return tipoOperacao;
    }

    public void setTipoOperacao(TipoOperacao tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
    }

    public LocalDate getDataOperacao() {
        return dataOperacao;
    }

    public void setDataOperacao(LocalDate dataOperacao) {
        this.dataOperacao = dataOperacao;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getNomePeca() {
        return nomePeca;
    }

    public void setNomePeca(String nomePeca) {
        this.nomePeca = nomePeca;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
}