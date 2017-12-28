(ns tests.all
  (:require
    [cljs.test :refer [deftest is testing run-tests async use-fixtures]]
    [day8.re-frame.test :refer [run-test-async wait-for run-test-sync]]
    [district.ui.window-size.events :as events]
    [district.ui.window-size.subs :as subs]
    [district.ui.window-size]
    [mount.core :as mount]
    [re-frame.core :refer [reg-event-fx dispatch-sync subscribe reg-cofx reg-fx dispatch]]))

(def breakpts
  {:max-large-monitor 1100
   :max-computer 900
   :max-tablet 700
   :max-mobile 500})

(use-fixtures
  :each
  {:after
   (fn []
     (mount/stop))})

(defn resize! [width]
  (aset js/window "innerWidth" width)
  (js/window.dispatchEvent (js/Event. "resize")))

(deftest tests
  (run-test-async
    (let [breakpoints (subscribe [::subs/breakpoints])
          size (subscribe [::subs/size])
          wide-monitor? (subscribe [::subs/wide-monitor?])
          large-monitor? (subscribe [::subs/large-monitor?])
          computer? (subscribe [::subs/computer?])
          tablet? (subscribe [::subs/tablet?])
          mobile? (subscribe [::subs/mobile?])
          max-large-monitor? (subscribe [::subs/max-large-monitor?])
          max-computer? (subscribe [::subs/max-computer?])
          max-tablet? (subscribe [::subs/max-tablet?])
          min-large-monitor? (subscribe [::subs/min-large-monitor?])
          min-computer? (subscribe [::subs/min-computer?])
          min-tablet? (subscribe [::subs/min-tablet?])]

      (-> (mount/with-args
            {:window-size {:breakpoints breakpts}})
        (mount/start))

      (= @breakpoints breakpts)

      (resize! 500)

      (wait-for [::events/size-changed]
        (is (= 0 @size))
        (is (true? @mobile?))
        (is (false? @tablet?))
        (is (true? @max-tablet?))

        (resize! 501)

        (wait-for [::events/size-changed]
          (is (= 1 @size))
          (is (false? @mobile?))
          (is (true? @tablet?))
          (is (true? @max-tablet?))
          (is (true? @min-tablet?))

          (resize! 900)

          (wait-for [::events/size-changed]
            (is (= 2 @size))
            (is (false? @tablet?))
            (is (true? @computer?))
            (is (true? @max-computer?))
            (is (true? @min-computer?))

            (resize! 1100)

            (wait-for [::events/size-changed]
              (is (= 3 @size))
              (is (false? @computer?))
              (is (true? @large-monitor?))
              (is (true? @max-large-monitor?))
              (is (true? @min-large-monitor?))

              (resize! 1101)

              (wait-for [::events/size-changed]
                (is (= 4 @size))
                (is (false? @large-monitor?))
                (is (true? @wide-monitor?))))))))))
