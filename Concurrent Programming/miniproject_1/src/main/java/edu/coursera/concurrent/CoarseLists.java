package edu.coursera.concurrent;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Wrapper class for two lock-based concurrent list implementations.
 */
public final class CoarseLists {
    /**
     * An implementation of the ListSet interface that uses Java locks to
     * protect against concurrent accesses.
     *
     * TODO Implement the add, remove, and contains methods below to support
     * correct, concurrent access to this list. Use a Java ReentrantLock object
     * to protect against those concurrent accesses. You may refer to
     * SyncList.java for help understanding the list management logic, and for
     * guidance in understanding where to place lock-based synchronization.
     */
    public static final class CoarseList extends ListSet {
        /*
         * TODO Declare a lock for this class to be used in implementing the
         * concurrent add, remove, and contains methods below.
         */
       private final ReentrantLock reentrantLock = new ReentrantLock();

        /**
         * Default constructor.
         */
        public CoarseList() {
            super();
        }

        /**
         * {@inheritDoc}
         *
         * TODO Use a lock to protect against concurrent access.
         */
        @Override
        boolean add(final Integer object) {
            try {
                reentrantLock.lock();
                Entry pred = this.head;
                Entry curr = pred.next;

                while (curr.object.compareTo(object) < 0) {
                    pred = curr;
                    curr = curr.next;
                }

                if (object.equals(curr.object)) {
                    return false;
                } else {
                    final Entry entry = new Entry(object);
                    entry.next = curr;
                    pred.next = entry;
                    return true;
                }
            } finally {
                reentrantLock.unlock();
            }

        }

        /**
         * {@inheritDoc}
         *
         * TODO Use a lock to protect against concurrent access.
         */
        @Override
        boolean remove(final Integer object) {
            try {
                reentrantLock.lock();
                Entry pred = this.head;
                Entry curr = pred.next;

                while (curr.object.compareTo(object) < 0) {
                    pred = curr;
                    curr = curr.next;
                }

                if (object.equals(curr.object)) {
                    pred.next = curr.next;
                    return true;
                } else {
                    return false;
                }
            } finally {
                reentrantLock.unlock();
            }
        }

        /**
         * {@inheritDoc}
         *
         * TODO Use a lock to protect against concurrent access.
         */
        @Override
        boolean contains(final Integer object) {
            try {
                reentrantLock.lock();
                Entry pred = this.head;
                Entry curr = pred.next;

                while (curr.object.compareTo(object) < 0) {
                    pred = curr;
                    curr = curr.next;
                }
                return object.equals(curr.object);
            } finally {
                reentrantLock.unlock();
            }

        }
    }

    /**
     * An implementation of the ListSet interface that uses Java read-write
     * locks to protect against concurrent accesses.
     *
     * TODO Implement the add, remove, and contains methods below to support
     * correct, concurrent access to this list. Use a Java
     * ReentrantReadWriteLock object to protect against those concurrent
     * accesses. You may refer to SyncList.java for help understanding the list
     * management logic, and for guidance in understanding where to place
     * lock-based synchronization.
     */
    public static final class RWCoarseList extends ListSet {
        /*
         * TODO Declare a read-write lock for this class to be used in
         * implementing the concurrent add, remove, and contains methods below.
         */
        private final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

        /**
         * Default constructor.
         */
        public RWCoarseList() {
            super();
        }

        /**
         * {@inheritDoc}
         *
         * TODO Use a read-write lock to protect against concurrent access.
         */
        @Override
        boolean add(final Integer object) {
            try {
                reentrantReadWriteLock.writeLock().lock();
                Entry pred = this.head;
                Entry curr = pred.next;

                while (curr.object.compareTo(object) < 0) {
                    pred = curr;
                    curr = curr.next;
                }

                if (object.equals(curr.object)) {
                    return false;
                } else {
                    final Entry entry = new Entry(object);
                    entry.next = curr;
                    pred.next = entry;
                    return true;
                }
            } finally {
                reentrantReadWriteLock.writeLock().unlock();
            }

        }

        /**
         * {@inheritDoc}
         *
         * TODO Use a read-write lock to protect against concurrent access.
         */
        @Override
        boolean remove(final Integer object) {
            try {
                reentrantReadWriteLock.writeLock().lock();
                Entry pred = this.head;
                Entry curr = pred.next;

                while (curr.object.compareTo(object) < 0) {
                    pred = curr;
                    curr = curr.next;
                }

                if (object.equals(curr.object)) {
                    pred.next = curr.next;
                    return true;
                } else {
                    return false;
                }
            } finally {
                reentrantReadWriteLock.writeLock().unlock();
            }

        }

        /**
         * {@inheritDoc}
         *
         * TODO Use a read-write lock to protect against concurrent access.
         */
        @Override
        boolean contains(final Integer object) {
            try {
                reentrantReadWriteLock.readLock().lock();
                Entry pred = this.head;
                Entry curr = pred.next;

                while (curr.object.compareTo(object) < 0) {
                    pred = curr;
                    curr = curr.next;
                }
                return object.equals(curr.object);
            } finally {
                reentrantReadWriteLock.readLock().unlock();
            }

        }
    }
}
