package lab3;
import java.util.EmptyStackException;

//Vaibhav Gogia
//400253615
//gogiav

public class MyStack<E> {
	private SNode<E> top;

    public MyStack() {
        this.top = null;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public void push(E val) {
        top = new SNode<>(val, null, top);
    }

    public E pop() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        }

        E val = top.element;
        top = top.next;
        return val;
    }

    public static class SNode<E> {
        E element;
        SNode<E> prev;
        SNode<E> next;

        public SNode(E element, SNode<E> prev, SNode<E> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }
    }
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////