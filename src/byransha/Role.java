package byransha;

import java.lang.reflect.ParameterizedType;

public class Role<N extends GOBMNode> {

	public N getNodeType() {
		return (N) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public static final Role<StringNode> name = new Role<>();
}