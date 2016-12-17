package net.matthiasauer.libgdx.tictactoe.utils

import spock.lang.Specification

/**
 * Created by Matthias on 13/12/2016.
 */
class GdxObservableImplTest extends Specification {
    def "test that an observer is notified"() {
        given:
            boolean called = false
            GdxObserver observer = new GdxObserver() {
                @Override
                void update() {
                    called = true
                }
            }
            GdxObservable observable = new GdxObservable() {

            }
            observable.addObserver(observer)
        when:
            observable.notifyObservers()
        then:
            called
    }

    def "test using multiple observers"() {
        given:
            boolean called1 = false
            boolean called2 = false
            boolean called3 = false
            GdxObserver observer1 = new GdxObserver() {
                @Override
                void update() {
                    called1 = true
                }
            }
            GdxObserver observer2 = new GdxObserver() {
                @Override
                void update() {
                    called2 = true
                }
            }
            GdxObserver observer3 = new GdxObserver() {
                @Override
                void update() {
                    called3 = true
                }
            }
            GdxObservable observable = new GdxObservable() {

            }
            observable.addObserver(observer1)
            observable.addObserver(observer2)
            observable.addObserver(observer3)
        when:
            observable.notifyObservers()
        then:
            called1
            called2
            called3
    }

    def "test using removing observers"() {
        given:
            boolean called1 = false
            boolean called2 = false
            boolean called3 = false
            GdxObserver observer1 = new GdxObserver() {
                @Override
                void update() {
                    called1 = true
                }
            }
            GdxObserver observer2 = new GdxObserver() {
                @Override
                void update() {
                    called2 = true
                }
            }
            GdxObserver observer3 = new GdxObserver() {
                @Override
                void update() {
                    called3 = true
                }
            }
            GdxObservable observable = new GdxObservable() {

            }
            observable.addObserver(observer1)
            observable.addObserver(observer2)
            observable.addObserver(observer3)
            observable.removeObserver(observer2)
        when:
            observable.notifyObservers()
        then:
            called1
            !called2
            called3
    }
}
