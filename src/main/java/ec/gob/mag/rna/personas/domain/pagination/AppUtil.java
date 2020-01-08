/**
 * 
 */
package ec.gob.mag.rna.personas.domain.pagination;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;

/**
 * Clase AppUtil.
 * 
 * @author Darwin Stalin Ramirez Supe - pavan.solapure
 * @see Paginacion creada para la BD postgreSQL Corregido
 */
public class AppUtil {

	/**
	 * Verifica si la colección esta vacía.
	 *
	 * @param collection
	 * @return true, Si la colección esta vacía
	 */
	private static boolean isCollectionEmpty(Collection<?> collection) {
		if (collection == null || collection.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * Verifica si el objeto esta vacío
	 *
	 * @param object
	 * @return true, Si esta vacío
	 */
	public static boolean isObjectEmpty(Object object) {
		if (object == null)
			return true;
		else if (object instanceof String) {
			if (((String) object).trim().length() == 0) {
				return true;
			}
		} else if (object instanceof Collection) {
			return isCollectionEmpty((Collection<?>) object);
		}
		return false;
	}

	/**
	 * Obtiene el bean del Json String
	 *
	 * @param beanClass
	 * @return the bean
	 */
	public static String getBeanToJsonString(Object beanClass) {
		return new Gson().toJson(beanClass);
	}

	/**
	 * Obtiene el bean del Json String
	 *
	 * @param beanClasses
	 * @return bean
	 */
	public static String getBeanToJsonString(Object... beanClasses) {
		StringBuilder stringBuilder = new StringBuilder();
		for (Object beanClass : beanClasses) {
			stringBuilder.append(getBeanToJsonString(beanClass));
			stringBuilder.append(", ");
		}
		return stringBuilder.toString();
	}

	/**
	 * Concatenar.
	 *
	 * @param listOfItems Lista de items
	 * @param separator   Separador
	 * @return la cadena String
	 */
	public String concatenate(List<String> listOfItems, String separator) {
		StringBuilder sb = new StringBuilder();
		Iterator<String> stit = listOfItems.iterator();

		while (stit.hasNext()) {
			sb.append(stit.next());
			if (stit.hasNext()) {
				sb.append(separator);
			}
		}

		return sb.toString();
	}

	/**
	 * Construye la consulta para la BD.
	 *
	 * @param baseQuery          Query
	 * @param paginationCriteria La paginación criteria
	 * @return string
	 */
	public static String buildPaginatedQuery(String baseQuery, PaginationCriteria paginationCriteria) {
		StringBuilder sb = new StringBuilder(
				"SELECT FILTERED_ORDERD_RESULTS.* FROM (SELECT BASEINFO.* FROM ( #BASE_QUERY# ) "
						+ "BASEINFO #WHERE_CLAUSE#  #ORDER_CLASUE# ) "
						+ "FILTERED_ORDERD_RESULTS LIMIT #PAGE_SIZE#  OFFSET #PAGE_NUMBER# ");
		String finalQuery = null;
		if (!AppUtil.isObjectEmpty(paginationCriteria)) {
			finalQuery = sb.toString().replaceAll("#BASE_QUERY#", baseQuery)
					.replaceAll("#WHERE_CLAUSE#",
							((AppUtil.isObjectEmpty(paginationCriteria.getFilterByClause())) ? "" : " WHERE ")
									+ paginationCriteria.getFilterByClause())
					.replaceAll("#ORDER_CLASUE#", paginationCriteria.getOrderByClause())
					.replaceAll("#PAGE_NUMBER#", paginationCriteria.getPageNumber().toString())
					.replaceAll("#PAGE_SIZE#", paginationCriteria.getPageSize().toString());
		}
		return (null == finalQuery) ? baseQuery : finalQuery;
	}
}
