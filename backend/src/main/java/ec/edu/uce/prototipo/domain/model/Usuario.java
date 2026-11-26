package ec.edu.uce.prototipo.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario extends PanacheEntityBase {

    @Id
    @SequenceGenerator(
            name = "seq_usuario_generador",
            sequenceName = "seq_usuario",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_usuario_generador"
    )
    @Column(name = "usu_id")
    private Integer id;

    @Column(name = "usu_nombre")
    private String nombre;

    @Column(name = "usu_correo")
    private String correo;

    @Column(name = "usu_clave")
    private String clave;

    @Column(name = "usu_rol")
    private String rol;

    public Usuario() {
    }

    public Usuario(Integer id, String nombre, String correo, String clave, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.clave = clave;
        this.rol = rol;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return this.clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getRol() {
        return this.rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public boolean esAdmin() {
        return "ADMIN".equals(this.rol);
    }

    @Override
    public String toString() {
        return "Usuario [id=" + this.id + ", nombre=" + this.nombre + ", correo=" + this.correo + ", rol=" + this.rol + "]";
    }
}
