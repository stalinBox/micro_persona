/**
 * 
 */
package ec.gob.mag.rna.personas.domain.pagination;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase SortBy.
 * 
 * @author Darwin Stalin Ramírez - pavan.solapure
 * @see Paginacion creada para la BD postgreSQL Corregido los errores de
 *      mayúsculas y minúsculas
 * @author pavan.solapure
 */
public class SortBy {

	/** The map of sorts. */
	private Map<String, SortOrder> mapOfSorts;

	/**
	 * Instantiates a new sort by.
	 */
	public SortBy() {
		if (null == mapOfSorts) {
			mapOfSorts = new HashMap<String, SortOrder>();
		}
	}

	/**
	 * Gets the sort bys.
	 *
	 * @return the sortBys
	 */
	public Map<String, SortOrder> getSortBys() {
		return mapOfSorts;
	}

	/**
	 * Adds the sort.
	 *
	 * @param sortBy the sort by
	 */
	public void addSort(String sortBy) {
		mapOfSorts.put(sortBy, SortOrder.DESC);
	}

	/**
	 * Adds the sort.
	 *
	 * @param sortBy    the sort by
	 * @param sortOrder the sort order
	 */
	public void addSort(String sortBy, SortOrder sortOrder) {
		mapOfSorts.put(sortBy, sortOrder);
	}

}
