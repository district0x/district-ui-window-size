(ns district.ui.window-size.subs
  (:require
    [district.ui.window-size.queries :as queries]
    [re-frame.core :refer [reg-sub]]))

(reg-sub
  ::breakpoints
  queries/breakpoints)

(reg-sub
  ::size
  queries/size)

(reg-sub
  ::wide-monitor?
  queries/wide-monitor?)

(reg-sub
  ::large-monitor?
  queries/large-monitor?)

(reg-sub
  ::computer?
  queries/computer?)

(reg-sub
  ::tablet?
  queries/tablet?)

(reg-sub
  ::mobile?
  queries/mobile?)

(reg-sub
  ::max-large-monitor?
  queries/max-large-monitor?)

(reg-sub
  ::max-computer?
  queries/max-computer?)

(reg-sub
  ::max-tablet?
  queries/max-tablet?)

(reg-sub
  ::min-large-monitor?
  queries/min-large-monitor?)

(reg-sub
  ::min-computer?
  queries/min-computer?)

(reg-sub
  ::min-tablet?
  queries/min-tablet?)
