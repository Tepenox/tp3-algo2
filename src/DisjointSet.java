import java.util.HashMap;
import java.util.Map;

public class DisjointSet<T> {

    private Map<T, T> parent = new HashMap();

    //create a subset of one element
    public void makeSet(T element)
    {
            parent.put(element, element);
    }

    public T find(T  k)
    {
        if (parent.get(k) == k)
            return k;

        return find(parent.get(k));
    }

    // Perform Union of two subsets
    public void union(T a, T b)
    {
        T x = find(a);
        T y = find(b);
        parent.put(x, y);
    }
}
