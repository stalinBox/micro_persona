package ec.gob.mag.rna.personas.util;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

@Component("util")
public class Util {

	public static String decodeJWTHeader(String jwtToken) {
		String[] splitToken = jwtToken.split("\\.");
		String encodedHeader = splitToken[0];
		Base64 base64Url = new Base64(true);
		String header = new String(base64Url.decode(encodedHeader));
		return header;
	}

	public String decodeJWTBody(String jwtToken) {
		String[] splitToken = jwtToken.split("\\.");
		String encodedBody = splitToken[1];
		Base64 base64Url = new Base64(true);
		String body = new String(base64Url.decode(encodedBody));
		return body;

	}

	public String decodeJWTSignature(String jwtToken) {
		String[] splitToken = jwtToken.split("\\.");
		String encodedSignature = splitToken[2];
		Base64 base64Url = new Base64(true);
		String signature = new String(base64Url.decode(encodedSignature));
		return signature;
	}

	public String filterUsuId(String token) {
		JSONObject data = new JSONObject(decodeJWTBody(token));
		String usuarios = checkKey(data, "usuarios").toString();
		JSONObject dataInto = new JSONObject(usuarios.substring(1, usuarios.length() - 1));
		return checkKey(dataInto, "usuId").toString();
	}

	public Object checkKey(JSONObject JsonObj, String searchedKey) {
		boolean exists = JsonObj.has(searchedKey);
		Object obj = null;
		if (JsonObj.isNull(searchedKey)) {
			return searchedKey = "";
		} else {
			if (exists) {
				obj = JsonObj.get(searchedKey);
			}
			if (!exists) {
				Set<String> keys = JsonObj.keySet();
				for (String key : keys) {
					if (JsonObj.get(key) instanceof JSONObject) {
						obj = checkKey((JSONObject) JsonObj.get(key), searchedKey);
					}
				}
			}
			return obj;
		}

	}

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
//	public boolean verificarCedula(String cedula) {
//		int total = 0;
//		int tamanoLongitudCedula = 10;
//		int[] coeficientes = { 2, 1, 2, 1, 2, 1, 2, 1, 2 };
//		int numeroProviancias = 24;
//		int tercerdigito = 6;
//		if (cedula.matches("[0-9]*") && cedula.length() == tamanoLongitudCedula) {
//			int provincia = Integer.parseInt(cedula.charAt(0) + "" + cedula.charAt(1));
//			int digitoTres = Integer.parseInt(cedula.charAt(2) + "");
//			if ((provincia > 0 && provincia <= numeroProviancias) && digitoTres < tercerdigito) {
//				int digitoVerificadorRecibido = Integer.parseInt(cedula.charAt(9) + "");
//				for (int i = 0; i < coeficientes.length; i++) {
//					int valor = Integer.parseInt(coeficientes[i] + "") * Integer.parseInt(cedula.charAt(i) + "");
//					total = valor >= 10 ? total + (valor - 9) : total + valor;
//				}
//				int digitoVerificadorObtenido = total >= 10 ? (total % 10) != 0 ? 10 - (total % 10) : (total % 10)
//						: total;
//				if (digitoVerificadorObtenido == digitoVerificadorRecibido) {
//					return true;
//				}
//			}
//			return false;
//		}
//		return false;
//	}

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

	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> map = new ConcurrentHashMap<>();
		return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

	/******************************/
	/**
	 * @param n&uacutemero de c&eacutedula
	 * @return true si es un documento v&aacutelido
	 * @throws Exception
	 */
	public boolean validarCedula(String numero) throws Exception {
		try {
			validarInicial(numero, 10);
			validarCodigoProvincia(numero.substring(0, 2));
			validarTercerDigito(String.valueOf(numero.charAt(2)), TipoDocumento.getTipoCedula());
			algoritmoModulo10(numero, Integer.parseInt(String.valueOf(numero.charAt(9))));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * @param numero de ruc persona natural
	 * @return true si es un documento v&aacutelido
	 * @throws Exception
	 */
	public boolean validarRucPersonaNatural(String numero) throws Exception {
		try {
			validarInicial(numero, 13);
			validarCodigoProvincia(numero.substring(0, 2));
			validarTercerDigito(String.valueOf(numero.charAt(2)), TipoDocumento.getTipoRucNatural());
			validarCodigoEstablecimiento(numero.substring(10, 13));
			algoritmoModulo10(numero.substring(0, 9), Integer.parseInt(String.valueOf(numero.charAt(9))));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * 
	 * @param numero ruc empresa privada
	 * @return
	 * @throws Exception
	 */
	public boolean validarRucSociedadPrivada(String numero) throws Exception {

		// validaciones
		try {
			validarInicial(numero, 13);
			validarCodigoProvincia(numero.substring(0, 2));
			validarTercerDigito(String.valueOf(numero.charAt(2)), TipoDocumento.getRucPrivada());
			validarCodigoEstablecimiento(numero.substring(10, 13));
			algoritmoModulo11(numero.substring(0, 9), Integer.parseInt(String.valueOf(numero.charAt(9))),
					TipoDocumento.getRucPrivada());
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	/**
	 * @param numero
	 * @param caracteres
	 * @return
	 * @throws Exception
	 */
	protected boolean validarInicial(String numero, int caracteres) throws Exception {
		if (StringUtils.isEmpty(numero)) {
			throw new Exception("Valor no puede estar vacio");
		}

		if (!NumberUtils.isDigits(numero)) {
			throw new Exception("Valor ingresado solo puede tener dígitos");
		}

		if (numero.length() != caracteres) {
			throw new Exception("Valor ingresado debe tener " + caracteres + " caracteres");
		}

		return true;
	}

	/**
	 * @param n&uacutemero en el rango de n&uacutemeros de provincias del ecuador
	 * @return
	 * @throws Exception
	 */
	protected boolean validarCodigoProvincia(String numero) throws Exception {
		if (Integer.parseInt(numero) < 0 || Integer.parseInt(numero) > 24) {
			throw new Exception("Codigo de Provincia (dos primeros dígitos) no deben ser mayor a 24 ni menores a 0");
		}

		return true;
	}

	/**
	 * @param numero
	 * @param tipo   de documento cedula, ruc
	 * @return
	 * @throws Exception
	 */
	protected boolean validarTercerDigito(String numero, Integer tipo) throws Exception {
		switch (tipo) {
		case 1:
		case 2:

			if (Integer.parseInt(numero) < 0 || Integer.parseInt(numero) > 5) {
				throw new Exception(
						"Tercer dígito debe ser mayor o igual a 0 y menor a 6 para cédulas y RUC de persona natural ... permitidos de 0 a 5");
			}
			break;
		case 3:
			if (Integer.parseInt(numero) != 9) {
				throw new Exception("Tercer dígito debe ser igual a 9 para sociedades privadas");
			}
			break;

		case 4:
			if (Integer.parseInt(numero) != 6) {
				throw new Exception("Tercer dígito debe ser igual a 6 para sociedades públicas");
			}
			break;
		default:
			throw new Exception("Tipo de Identificacion no existe.");
		}

		return true;
	}

	/**
	 * @param digitosIniciales
	 * @param digitoVerificador
	 * @return
	 * @throws Exception
	 */
	protected boolean algoritmoModulo10(String digitosIniciales, int digitoVerificador) throws Exception {
		Integer[] arrayCoeficientes = new Integer[] { 2, 1, 2, 1, 2, 1, 2, 1, 2 };

		Integer[] digitosInicialesTMP = new Integer[digitosIniciales.length()];
		int indice = 0;
		for (char valorPosicion : digitosIniciales.toCharArray()) {
			digitosInicialesTMP[indice] = NumberUtils.createInteger(String.valueOf(valorPosicion));
			indice++;
		}

		int total = 0;
		int key = 0;
		for (Integer valorPosicion : digitosInicialesTMP) {
			if (key < arrayCoeficientes.length) {
				valorPosicion = (digitosInicialesTMP[key] * arrayCoeficientes[key]);

				if (valorPosicion >= 10) {
					char[] valorPosicionSplit = String.valueOf(valorPosicion).toCharArray();
					valorPosicion = (Integer.parseInt(String.valueOf(valorPosicionSplit[0])))
							+ (Integer.parseInt(String.valueOf(valorPosicionSplit[1])));

				}
				total = total + valorPosicion;
			}

			key++;
		}
		int residuo = total % 10;
		int resultado;

		if (residuo == 0) {
			resultado = 0;
		} else {
			resultado = 10 - residuo;
		}

		if (resultado != digitoVerificador) {
			throw new Exception("Dígitos iniciales no validan contra Dígito Idenficador");
		}

		return true;
	}

	/**
	 * @param numero
	 * @return
	 * @throws Exception
	 */
	protected boolean validarCodigoEstablecimiento(String numero) throws Exception {
		if (Integer.parseInt(numero) < 1) {
			throw new Exception("Código de establecimiento no puede ser 0");
		}
		return true;
	}

	/**
	 * @param digitosIniciales
	 * @param digitoVerificador
	 * @param tipo
	 * @return
	 * @throws Exception
	 */
	protected boolean algoritmoModulo11(String digitosIniciales, int digitoVerificador, Integer tipo) throws Exception {
		Integer[] arrayCoeficientes = null;

		switch (tipo) {

		case 3:
			arrayCoeficientes = new Integer[] { 4, 3, 2, 7, 6, 5, 4, 3, 2 };
			break;
		case 4:
			arrayCoeficientes = new Integer[] { 3, 2, 7, 6, 5, 4, 3, 2 };
			break;
		default:
			throw new Exception("Tipo de Identificacion no existe.");
		}

		Integer[] digitosInicialesTMP = new Integer[digitosIniciales.length()];
		int indice = 0;
		for (char valorPosicion : digitosIniciales.toCharArray()) {
			digitosInicialesTMP[indice] = NumberUtils.createInteger(String.valueOf(valorPosicion));
			indice++;
		}

		int total = 0;
		int key = 0;
		for (Integer valorPosicion : digitosInicialesTMP) {
			if (key < arrayCoeficientes.length) {
				valorPosicion = (digitosInicialesTMP[key] * arrayCoeficientes[key]);

				if (valorPosicion >= 10) {
					char[] valorPosicionSplit = String.valueOf(valorPosicion).toCharArray();
					valorPosicion = (Integer.parseInt(String.valueOf(valorPosicionSplit[0])))
							+ (Integer.parseInt(String.valueOf(valorPosicionSplit[1])));
					System.out.println(valorPosicion);
				}
				total = total + valorPosicion;
			}

			key++;
		}

		int residuo = total % 11;
		int resultado;

		if (residuo == 0) {
			resultado = 0;
		} else {
			resultado = (11 - residuo);
		}

		if (resultado != digitoVerificador) {
			throw new Exception("Dígitos iniciales no validan contra Dígito Idenficador");
		}

		return true;
	}
}
