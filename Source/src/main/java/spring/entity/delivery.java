package spring.entity;

/**
 * Created by olulrich on 20.10.17.
 */
public class delivery {

    private int id;
    private String cargo;

    public delivery(int id, String cargo) {
        this.id = id;
        this.cargo = cargo;
    }

    public delivery(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
