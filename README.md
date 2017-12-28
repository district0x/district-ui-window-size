# district-ui-window-size

[![Build Status](https://travis-ci.org/district0x/district-ui-window-size.svg?branch=master)](https://travis-ci.org/district0x/district-ui-window-size)

Clojurescript [mount](https://github.com/tolitius/mount) + [re-frame](https://github.com/Day8/re-frame) module for a district UI, that provides window size in form of breakpoints.

## Installation
Add `[district0x/district-ui-window-size "1.0.0"]` into your project.clj  
Include `[district.ui.window-size]` in your CLJS file, where you use `mount/start`

**Warning:** district0x modules are still in early stages, therefore API can change in a future.

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

#### `::breakpoints`
Returns breakpoints.

#### `::size`
Returns current window size (0-4) as defined by breakpoints.

#### `::wide-monitor?`
True if window is wide monitor.

#### `::large-monitor?`
True if window is large monitor.

#### `::computer?`
True if window is computer.

#### `::tablet?`
True if window is tablet.

#### `::mobile?`
True if window is mobile.

#### `::max-large-monitor?`
True if window is anything up to large monitor (including).

#### `::max-computer?`
True if window is anything up to computer (including).

#### `::max-tablet?`
True if window is anything up to tablet (including).

#### `::min-large-monitor?`
True if window is large monitor or bigger.

#### `::min-computer?`
True if window is computer or bigger.

#### `::min-tablet?`
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

#### `::start [opts]`
Event fired at mount start.

#### `::window-resized [width height]`
Event fired when window has been resized

#### `::size-changed [new-size old-size]`
Event fired when size, as defined by breakpoints, was actually changed. You can use this event to hook into event flow, e.g with [re-frame-forward-events-fx](https://github.com/Day8/re-frame-forward-events-fx).

#### `::stop`
Cleanup event fired on mount stop.

## district.ui.window-size.queries
DB queries provided by this module:  
*You should use them in your events, instead of trying to get this module's 
data directly with `get-in` into re-frame db.*

#### `breakpoints [db]`
Works the same way as sub `::breakpoints`

#### `size [db]`
Works the same way as sub `::size`

#### `calculate-size [db window-width & [breakpoints]]`
Calculates window size (0-4) given width in pixels. Optionally, you can pass breakpoints, otherwise breakpoints from
initial configuration will be used.

#### `wide-monitor? [db]`
Works the same way as sub `::wide-monitor?`

#### `large-monitor? [db]`
Works the same way as sub `::large-monitor?`

#### `computer? [db]`
Works the same way as sub `::computer?`

#### `tablet? [db]`
Works the same way as sub `::tablet?`

#### `mobile? [db]`
Works the same way as sub `::mobile?`

#### `max-large-monitor? [db]`
Works the same way as sub `::max-large-monitor?`

#### `max-computer? [db]`
Works the same way as sub `::max-computer?`

#### `max-tablet? [db]`
Works the same way as sub `::max-tablet?`

#### `min-large-monitor? [db]`
Works the same way as sub `::min-large-monitor?`

#### `min-computer? [db]`
Works the same way as sub `::min-computer?`

#### `min-tablet? [db]`
Works the same way as sub `::min-tablet?`

#### `assoc-breakpoints [db breakpoints]`
Associates breakpoints and returns new re-frame db.

#### `assoc-size [db size]`
Associates size and returns new re-frame db.

#### `assoc-window-size [db {:keys [:breakpoints :size]}]`
Associates size, breakpoints and returns new re-frame db.

#### `dissoc-window-size [db]`
Cleans up this module from re-frame db. 

## Development
```bash
lein deps

# To run tests and rerun on changes
lein doo chrome tests
```