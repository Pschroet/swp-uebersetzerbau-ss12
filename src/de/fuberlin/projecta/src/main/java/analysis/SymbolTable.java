package analysis;

import java.util.ArrayList;
import java.util.List;

import analysis.ast.nodes.Id;
import analysis.ast.nodes.Type;

/**
 * @author Christian Cikryt
 */
public class SymbolTable {

	// private HashMap<String, EntryType> entries;
	private List<EntryType> entries;

	public SymbolTable() {
		entries = new ArrayList<EntryType>();
	}

	/**
	 * Updates the entry associated with name. If it is not contained yet it
	 * gets added, otherwise the current value will be overridden.
	 * 
	 * @param name
	 * @param entry
	 */
	public void updateEntry(EntryType value) throws IllegalStateException {
		// TODO: currently not implemented
		throw new SemanticException("currently not implemented, sry");
	}

	public void insertEntry(Id id, Type type, List<EntryType> params)
			throws IllegalStateException {
		
		if (lookup(id.getValue(), params) == null) {
			entries.add(new EntryType(id, type, params));
		} else {
			throw new IllegalStateException(id.getValue()
					+ " is already registered in this symbolTable. "
					+ "You may want to update instead.");
		}
	}

	public void insertEntry(Id id, Type type) throws IllegalStateException {
		if (lookup(id.getValue()) == null) {
			entries.add(new EntryType(id, type));
		} else {
			throw new IllegalStateException(id.getValue()
					+ " is already registered in this symbolTable. "
					+ "You may want to update instead.");
		}

	}

	public void insertEntry(EntryType entry) {
		if (lookup(entry.getId(), entry.getParams()) == null) {
			entries.add(entry);
		} else {
			throw new IllegalStateException(entry.getId()
					+ " is already registered in this symbolTable. "
					+ "You may want to update instead. 3" + lookup(entry.getId(), entry.getParams()) + ":" + entry);
		}
	}

	/**
	 * Find value for a specific key. If multiple instances of this node exist,
	 * the first one is taken.
	 * 
	 * @param name
	 *            Key
	 * @return Value, may be null
	 */
	public EntryType lookup(String name) {
		for (EntryType entry : entries) {
			if (entry.getId().equals(name)) {
				return entry;
			}
		}
		return null;
	}

	/**
	 * This is basically the method to look for function definitions.
	 * 
	 * @param name
	 *            function name
	 * @param params
	 *            function parameter
	 * @return The Entry for this method
	 */
	public EntryType lookup(String name, List<EntryType> params) {
		EntryType ret = null;
		for (EntryType entry : entries) {
			if (entry.getId().equals(name)) {
				boolean found = true;
				if (entry.getParams().size() == params.size()) {
					for (int i = 0; i < params.size(); i++) {
						if (!entry.getParams().get(i).equals(params.get(i))) {
							found = false;
						}
					}
				} else {
					found = false;
				}
				if(found) ret = entry;
			}
		}
		return ret;
	}

	public List<EntryType> getEntries() {
		return entries;
	}

	@Override
	public String toString() {
		String ret = "";
		for (EntryType entry : entries) {
			ret += entry.getId() + ", ";
		}
		return ret;
	}
}