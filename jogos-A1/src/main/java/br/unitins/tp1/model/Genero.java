package br.unitins.tp1.model;

public enum Genero {
    RPG(1, "RPG"),
    ACAO(2, "Ação"),
    AVENTURA(3, "Aventura"),
    CORRIDA(4, "Corrida"),
    ESTRATEGIA(5, "Estratégia");

    private final int id;
    private final String label;

    Genero(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static Genero valueOf(Integer id) {
        if (id == null)
            return null;
        for (Genero g : Genero.values()) {
            if (g.getId() == id)
                return g;
        }
        throw new IllegalArgumentException("Gênero inválido: " + id);
    }
}