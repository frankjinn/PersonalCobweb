package org.cobweb.cobweb2.interconnect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.cobweb.cobweb2.core.params.ComplexAgentParams;
import org.cobweb.cobweb2.io.CobwebSelectionParam;
import org.cobweb.io.ConfDisplayName;
import org.cobweb.io.ConfXMLTag;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class Phenotype implements CobwebSelectionParam<Phenotype> {

	private static final long serialVersionUID = -6142169580857190598L;

	public Field field = null;

	@Deprecated //FIXME static!
	private final static List<Phenotype> allPhenos;

	static {
		List<Phenotype> bindables = new ArrayList<Phenotype>();
		// Null phenotype
		bindables.add(new Phenotype());

		// @GeneMutatable fields
		for (Field f: ComplexAgentParams.class.getFields()) {
			if (f.getAnnotation(GeneMutatable.class) != null)
				bindables.add(new Phenotype(f));
		}
		allPhenos = Collections.unmodifiableList(bindables);
	}

	public Phenotype(){
		// Nothing
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Phenotype) {
			Phenotype p = (Phenotype) obj;
			if (p.field == null && this.field == null) return true;
			if (p.field == null || this.field == null) return false;
			return p.field.equals(this.field);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return field.hashCode();
	}

	/**
	 *
	 * @param f field to modify
	 */
	public Phenotype(Field f) {
		if (f != null &&
				(f.getAnnotation(GeneMutatable.class) == null || f.getAnnotation(ConfDisplayName.class) == null))
			throw new IllegalArgumentException("Field must be labeled as @GeneMutatable and have a @ConfDisplayName");
		this.field = f;
	}

	@Override
	public void loadConfig(Node root) throws IllegalArgumentException {
		String value = root.getTextContent();
		for (Phenotype p : allPhenos) {
			if (value.equals("None")) {
				this.field = null;
				return;
			} else if (p.field != null) {
				if (p.field.getAnnotation(ConfXMLTag.class).value().equals(value)) {
					this.field = p.field;
					return;
				}
				if (p.field.getAnnotation(ConfDisplayName.class).value().equals(value)) {
					this.field = p.field;
					return;
				}
			}
		}
		throw new IllegalArgumentException("Cannot match Phenotype '" + value + "' to any field");
	}

	@Override
	public String toString() {
		if (field == null)
			return "[Null]";

		return field.getAnnotation(ConfDisplayName.class).value();
	}

	@Override
	public void saveConfig(Node root, Document document) {
		String value;
		if (field == null) {
			value = "None";
		} else {
			value = field.getAnnotation(ConfXMLTag.class).value();
		}
		root.setTextContent(value);
	}

	@Override
	public List<Phenotype> getPossibleValues() {
		return allPhenos;
	}
}