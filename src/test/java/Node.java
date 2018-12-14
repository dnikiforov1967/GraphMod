
import java.util.Collection;
import java.util.Objects;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dnikiforov
 */
public class Node {
	
	private final Integer id;
	private Collection<Node> children;

	public Node(Integer id) {
		this.id = id;
	}

	public void setChildren(Collection<Node> children) {
		this.children = children;
	}

	public Integer getId() {
		return id;
	}

	public Collection<Node> getChildren() {
		return children;
	}

	public void proceed() {
		System.out.println(this);
	}
	
	@Override
	public int hashCode() {
		int hash = 3;
		hash = 89 * hash + Objects.hashCode(this.id);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Node other = (Node) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "NodeOfTree{" + "id=" + id + '}';
	}
	
}
