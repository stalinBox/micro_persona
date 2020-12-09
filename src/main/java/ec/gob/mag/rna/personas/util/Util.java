package ec.gob.mag.rna.personas.util;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import ec.gob.mag.rna.personas.domain.Persona;
import ec.gob.mag.rna.personas.domain.PersonaTipo;

public class Util {

	public static void copyObject(Object src, Object dest)
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		for (Field field : src.getClass().getFields()) {
			dest.getClass().getField(field.getName()).set(dest, field.get(src));
		}
	}

	/**
	 * METODO PARA VERIFICAR LA CEDULA DE CIUDADANIA ECUATORIANA
	 * 
	 * @param cedula
	 * @return boolean
	 */
	public boolean verificarCedula(String cedula) {
		int total = 0;
		int tamanoLongitudCedula = 10;
		int[] coeficientes = { 2, 1, 2, 1, 2, 1, 2, 1, 2 };
		int numeroProviancias = 24;
		int tercerdigito = 6;
		if (cedula.matches("[0-9]*") && cedula.length() == tamanoLongitudCedula) {
			int provincia = Integer.parseInt(cedula.charAt(0) + "" + cedula.charAt(1));
			int digitoTres = Integer.parseInt(cedula.charAt(2) + "");
			if ((provincia > 0 && provincia <= numeroProviancias) && digitoTres < tercerdigito) {
				int digitoVerificadorRecibido = Integer.parseInt(cedula.charAt(9) + "");
				for (int i = 0; i < coeficientes.length; i++) {
					int valor = Integer.parseInt(coeficientes[i] + "") * Integer.parseInt(cedula.charAt(i) + "");
					total = valor >= 10 ? total + (valor - 9) : total + valor;
				}
				int digitoVerificadorObtenido = total >= 10 ? (total % 10) != 0 ? 10 - (total % 10) : (total % 10)
						: total;
				if (digitoVerificadorObtenido == digitoVerificadorRecibido) {
					return true;
				}
			}
			return false;
		}
		return false;
	}

	public static String cleanBlanks(String str) {
		str = str.replaceAll(" +", " ");
		str = str.trim();
		return str;
	}

	public static int randomBetween(int start, int end) {
		int dif = end - start;
		if (dif > 0) {
			Random random = new Random();
			return start + random.nextInt((int) dif);
		}
		return -1;
	}

	public static String generateStringRandom(int numberCharacteres) {
		char character;
		int numberRandom;
		String password = "";
		for (int i = 0; i < numberCharacteres; i++) {
			numberRandom = randomBetween(33, 125);
			character = (char) numberRandom;
			password = password + character;
		}
		return password;
	}

	public static Date dateNow() {
		Date dateIn = new Date();
		LocalDateTime ldt = LocalDateTime.ofInstant(dateIn.toInstant(), ZoneId.systemDefault());
		return Date.from(ldt.atZone(ZoneId.of("UTC-05:00")).toInstant());
	}

	public static Persona parseToPersona(Persona productor) {
		if (productor == null)
			return null;
		Persona persona = new Persona();
		if (productor != null) {
			persona.setId(productor.getId());
			persona.setPerActFecha(productor.getPerActFecha());
			persona.setPerApellido(productor.getPerApellido());
			persona.setPerCedula(productor.getPerCedula());
			persona.setPerCelular(productor.getPerCelular());
			persona.setPerCorreo(productor.getPerCorreo());
			persona.setPerDirDomicilio(productor.getPerDirDomicilio());
			persona.setPerFechaNac(productor.getPerFechaNac());
			persona.setPerIdentificacion(productor.getPerIdentificacion());
			persona.setPerLugarNacRc(productor.getPerLugarNacRc());
			persona.setPerNombre(productor.getPerNombre());
			persona.setPerNombres(productor.getPerNombres());
			persona.setPerRegFecha(productor.getPerRegFecha());
			persona.setPerTelefono(productor.getPerTelefono());
			persona.setPerTipoContrSri(productor.getPerTipoContrSri());
			persona.setUbiIdDomicilio(productor.getUbiIdDomicilio());
			persona.setTipoProductor(productor.getTipoProductor());
			List<PersonaTipo> personasTipo = new ArrayList<>();
			PersonaTipo personaTipo = new PersonaTipo();
			personaTipo.setId(productor.getPetiId());
			personasTipo.add(personaTipo);
			persona.setPersonaTipos(personasTipo);

		}
		return persona;
	}

	public static List<Persona> parseToListPersona(List<Persona> productores) {
		if (productores == null)
			return null;
		return productores.stream().map(p -> {
			return parseToPersona(p);
		}).collect(Collectors.toList());
	}

	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> map = new ConcurrentHashMap<>();
		return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

}