package com.example.demo.dto.empleado;

public class EmpleadoRequest {

    private String nombreEmpleado;
    private String curp;
    private String correoEmpleado;
    private String contrasena;
    private Integer idRol;

    public String getNombreEmpleado()        { return nombreEmpleado; }
    public void setNombreEmpleado(String v)  { this.nombreEmpleado = v; }

    public String getCurp()                  { return curp; }
    public void setCurp(String v)            { this.curp = v; }

    public String getCorreoEmpleado()        { return correoEmpleado; }
    public void setCorreoEmpleado(String v)  { this.correoEmpleado = v; }

    public String getContrasena()            { return contrasena; }
    public void setContrasena(String v)      { this.contrasena = v; }

    public Integer getIdRol()                { return idRol; }
    public void setIdRol(Integer v)          { this.idRol = v; }
}
