# Plantilla Base para la coodificación de microservicios.

## Activar Lombok

1. Sts.ini
2. Agregar Lineas
'''
	-javaagent:E:\lmb\lombok.jar
	-Xbootclasspath/a:E:\lmb\lombok.jar
'''

## Ejemplo
### findById
578042


### findCedula
0924962848

### create
'''
{
  "perIdentificacion": "0433962848",

  "perCedula": "0433962848",
  "catTipoIdentificacion": 18,
  "catGenero": 22,
  "catEstadoCivil": 23,
  "catTitulo": null,
  "catAbrevTitulo": null,
  "catTituloAcad": null,
  "catAbrevTituloAcad": null,
  "catIdTipoNac": null,
  "catEtnia": null,
  "catPuebloIndigena": null,
  "perNombre": "JOHANNA YOLANDA",
  "perApellido": "COPPIANO ARELLANO",
  "perNombres": "COPPIANO ARELLANO JOHANNA YOLANDA",
  
  "perFechaNac": "1992-01-25",
  "perTipoSangre": null,
  "perFuente": null,
  "perFuenteId": null,
  "perFuenteFecha": null,
  "perRccondicion": "CIUDADANO",
  "perEstado": 11,
  "perEliminado": false,
  "perRegUsu": 0,
  "perRegFecha": "2018-10-16T21:02:33.279+0000",
  "perActUsu": null,
  "perActFecha": null,
  "ubiIdDomicilio": null,
  "perDirDomicilio": null,
  "perTelefono": "123456789",
  "perCelular": "0987654321",
  "perCorreo": "byron_np@gmail.com",
  "perFechaDefuncion": null,
  "catTipoOrgPer": null,
  "perRuc": "0924962848001",
  "perTipoSri": "PNL",
  "perActEconSri": "",
  "perEstadoSri": "",
  "perTipoContrSri": "PERSONAS NATURALES",
  "perPaginaWeb": null,
  "perLugarNacRc": "GUAYAS/GUAYAQUIL/TARQUI",
  "catInstruccionFormal": null,
  "personaTipos": [
    {
      "areaId": 2,
      "cargoId": 7,
      "catTipoPer": 40,
      "catRelacionDirectiva": 1,
      "petiObservacion": null,
      "petiFuente": null,
      "petiFuenteFecha": null,
      "petiEstado": 11,
      "petiEliminado": false,
      "petiRegUsu": null,
      "petiActUsu": null,
	  "productor": {
        "catActEconomica": 0,
        "catFuenteIngreso": null,
        "proViveTerreno": "",
        "proEstado": 11,
        "proEliminado": false,
        "proRegUsu": null,
        "proActUsu": null,
        "proNumPersonasRemuneradas": 0,
        "proNumPersonasNoRemuneradas": 0,
        "proNumRemuneradasTemporal": null,
        "proTotalManoObra": null
      }
      
    }
   ]
  
}

'''


# Resolver problema de lombok

* Incluir pom en Maven.
* Salir/apagado STS
* Encuentra Jar Lombok en ~/.m2/repositorio/org/projectlombok/Lombok/version.x
* desde el símbolo/Shell java -jar lombok-1.x.y.jar 