package ec.gob.mag.rna.personas.util;

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

	public static String cleanBlanks(String str) {
		str = str.replaceAll(" +", " ");
		str = str.trim();
		return str;
	}

	/**
	 * @author Paul Cuichan
	 * 
	 */
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
//			personaTipo.setAreaId(productor.getAreaId());
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