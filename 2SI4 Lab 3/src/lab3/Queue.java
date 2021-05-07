package lab3;

//Vaibhav Gogia
//400253615
//gogiav

import java.util.NoSuchElementException;

public class Queue<E> {

    private int n;
    private QNode<E> first, last;

    public Queue(){
        first = null;
        last = null;
        n=0;
    }

    public boolean isEmpty(){
        return first == null;
    }

    public int size(){
        return n;
    }

    public E peek() throws NoSuchElementException{
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        return first.element;
    }

    public void enqueue(E e){
        QNode<E> old = last;
        last = new QNode<>();
        last.element = e;
        last.next = null;
        if(isEmpty()) first = last;
        else old.next = last;
        n++;
    }

    public E dequeue() throws NoSuchElementException{
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        E e = first.element;
        first = first.next;
        n--;
        if (isEmpty()) last = null;
        return e;
    }

    public static class QNode<E> {
        E element;
        private QNode<E> next;
    }
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////