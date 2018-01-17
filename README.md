# district-ui-window-size

[![Build Status](https://travis-ci.org/district0x/district-ui-window-size.svg?branch=master)](https://travis-ci.org/district0x/district-ui-window-size)

Clojurescript [mount](https://github.com/tolitius/mount) + [re-frame](https://github.com/Day8/re-frame) module for a district UI, that provides window size in form of breakpoints.

## Installation
Add `[district0x/district-ui-window-size "1.0.1"]` into your project.clj  
Include `[district.ui.window-size]` in your CLJS file, where you use `mount/start`

## API Overview

**Warning:** district0x modules are still in early stages, therefore API can change in a future.

- [district.ui.window-size](#districtuiwindow-size)
- [district.ui.window-size.subs](#districtuiwindow-sizesubs)
  - [::breakpoints](#breakpoints-sub)
  - [::size](#size-sub)
  - [::wide-monitor?](#wide-monitor?-sub)
  - [::large-monitor?](#large-monitor?-sub)
  - [::computer?](#computer?-sub)
  - [::tablet?](#tablet?-sub)
  - [::mobile?](#mobile?-sub)
  - [::max-large-monitor?](#max-large-monitor?-sub)
  - [::max-computer?](#max-computer?-sub)
  - [::max-tablet?](#max-tablet?-sub)
  - [::min-large-monitor?](#min-large-monitor?-sub)
  - [::min-computer?](#min-computer?-sub)
  - [::min-tablet?](#min-tablet?-sub)
- [district.ui.window-size.events](#districtuiwindow-sizeevents)
  - [::window-resized](#window-resized)
  - [::size-changed](#size-changed)
- [district.ui.window-size.queries](#districtuiwindow-sizequeries)
  - [breakpoints](#breakpoints)
  - [size](#size)
  - [wide-monitor?](#wide-monitor?)
  - [large-monitor?](#large-monitor?)
  - [computer?](#computer?)
  - [tablet?](#tablet?)
  - [mobile?](#mobile?)
  - [max-large-monitor?](#max-large-monitor?)
  - [max-computer?](#max-computer?)
  - [max-tablet?](#max-tablet?)
  - [min-large-monitor?](#min-large-monitor?)
  - [min-computer?](#min-computer?)
  - [min-tablet?](#min-tablet?)
  - [assoc-breakpoints](#assoc-breakpoints)
  - [assoc-size](#assoc-size)
  - [assoc-window-size](#assoc-window-size)

## district.ui.window-size
This namespace contains window-size [mount](https://github.com/tolitius/mount) module.

You can pass following args to initiate this module: 
* `:breakpoints` A hashmap describing window breakpoints in pixels. Default: 
```clojure
{:max-large-monitor 1920
 :max-computer 1200
 :max-tablet 992
 :max-mobile 768}
``` 
* `:debounce-ms` Debounce period of resize event to prevent too many firings when window is being resized. Default: 166


```clojure
  (ns my-district.core
    (:require [mount.core :as mount]
              [district.ui.window-size]))

  (-> (mount/with-args
        ;; This is default, so not really needed
        {:window-size {:breakpoints {:max-large-monitor 1920
                                     :max-computer 1200
                                     :max-tablet 992
                                     :max-mobile 768}}})
    (mount/start))
```

## district.ui.window-size.subs
re-frame subscriptions provided by this module:

#### <a name="breakpoints-sub">`::breakpoints []`
Returns breakpoints.

#### <a name="size-sub">`::size []`
Returns current window size (0-4) as defined by breakpoints.

#### <a name="wide-monitor?-sub">`::wide-monitor? []`
True if window is wide monitor.

#### <a name="large-monitor?-sub">`::large-monitor? []`
True if window is large monitor.

#### <a name="computer?-sub">`::computer? []`
True if window is computer.

#### <a name="tablet?-sub">`::tablet? []`
True if window is tablet.

#### <a name="mobile?-sub">`::mobile? []`
True if window is mobile.

#### <a name="max-large-monitor?-sub">`::max-large-monitor? []`
True if window is anything up to large monitor (including).

#### <a name="max-computer?-sub">`::max-computer? []`
True if window is anything up to computer (including).

#### <a name="max-tablet?-sub">`::max-tablet? []`
True if window is anything up to tablet (including).

#### <a name="min-large-monitor?-sub">`::min-large-monitor? []`
True if window is large monitor or bigger.

#### <a name="min-computer?-sub">`::min-computer? []`
True if window is computer or bigger.

#### <a name="min-tablet?-sub">`::min-tablet? []`
True if window is tablet or bigger.

```clojure
(ns my-district.core
    (:require [mount.core :as mount]
              [district.ui.window-size.subs :as size-subs]))
  
  (defn home-page []
    (let [max-tablet? (subscribe [::size-subs/max-tablet?])]  
      (fn []
        (if @max-tablet?
          [:div "This screen is tablet or mobile"]
          [:div "This screen is computer, large monitor or wide monitor"]))))
```

## district.ui.window-size.events
re-frame events provided by this module:

#### <a name="window-resized">`::window-resized [width height]`
Event fired when window has been resized

#### <a name="size-changed">`::size-changed [new-size old-size]`
Event fired when size, as defined by breakpoints, was actually changed. You can use this event to hook into event flow, e.g with [re-frame-forward-events-fx](https://github.com/Day8/re-frame-forward-events-fx).

## district.ui.window-size.queries
DB queries provided by this module:  
*You should use them in your events, instead of trying to get this module's 
data directly with `get-in` into re-frame db.*

#### <a name="breakpoints">`breakpoints [db]`
Works the same way as sub `::breakpoints`

#### <a name="size">`size [db]`
Works the same way as sub `::size`

#### <a name="calculate-size">`calculate-size [db window-width & [breakpoints]]`
Calculates window size (0-4) given width in pixels. Optionally, you can pass breakpoints, otherwise breakpoints from
initial configuration will be used.

#### <a name="wide-monitor?">`wide-monitor? [db]`
Works the same way as sub `::wide-monitor?`

#### <a name="large-monitor?">`large-monitor? [db]`
Works the same way as sub `::large-monitor?`

#### <a name="computer?">`computer? [db]`
Works the same way as sub `::computer?`

#### <a name="tablet?">`tablet? [db]`
Works the same way as sub `::tablet?`

#### <a name="mobile?">`mobile? [db]`
Works the same way as sub `::mobile?`

#### <a name="max-large-monitor?">`max-large-monitor? [db]`
Works the same way as sub `::max-large-monitor?`

#### <a name="max-computer?">`max-computer? [db]`
Works the same way as sub `::max-computer?`

#### <a name="max-tablet?">`max-tablet? [db]`
Works the same way as sub `::max-tablet?`

#### <a name="min-large-monitor?">`min-large-monitor? [db]`
Works the same way as sub `::min-large-monitor?`

#### <a name="min-computer?">`min-computer? [db]`
Works the same way as sub `::min-computer?`

#### <a name="min-tablet?">`min-tablet? [db]`
Works the same way as sub `::min-tablet?`

#### <a name="assoc-breakpoints">`assoc-breakpoints [db breakpoints]`
Associates breakpoints and returns new re-frame db.

#### <a name="assoc-size">`assoc-size [db size]`
Associates size and returns new re-frame db.

#### <a name="assoc-window-size">`assoc-window-size [db {:keys [:breakpoints :size]}]`
Associates size, breakpoints and returns new re-frame db.

## Development
```bash
lein deps

# To run tests and rerun on changes
lein doo chrome tests
```