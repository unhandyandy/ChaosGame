(ns chaos-game.core
  (:use seesaw.core
        seesaw.graphics
        seesaw.color
        seesaw.keystroke)
  (:gen-class))

(native!)

(def mouseMode (atom :polygon))
(def numCorners (atom 2))
(def ratio (atom 0.5))
(def points (atom []))

(def cnv (canvas :id :cnv :background "#FFF"
                 :paint (fn [c g]
                          (doseq [[x y c] @points]
                            (draw g (circle x y 2) (style :foreground c
                                                          :background c)))
                          (if (= @mouseMode :addOne)
                            (let [[x y] (last @points)]
                              (draw g (circle x y 2) (style :foreground "#2f2"
                                                            :background "#2f2")))))
                 :size [600 :by 600]))

(defn clear []
  (reset! points [])
  (repaint! cnv))

(defn nextPoint []
    (let [lastpoint (subvec (last @points) 0 2)
          randcorner (subvec (get @points (rand-int @numCorners)) 0 2)
          newpoint (map + (map #(* @ratio %) lastpoint)
                        (map #(* (- 1 @ratio) %) randcorner))]
      (swap! points conj (conj (vec newpoint) "#000"))
      (repaint! cnv)
      ))

(defn progress []
  (let [i (atom 0)]
    (while (< @i 100)
      (do (nextPoint)
          (swap! i + 1))
      )))

(def ratioEntry  (horizontal-panel
                  :items [(label "Ratio: ")
                          (text :text "" :editable? true
                                :listen [:key-pressed
                                         (fn [e]
                                           (let [k (.getKeyChar e)]
                                             (if (= k \newline)
                                               (let [r (read-string (text e))]
                                                 (if (number? r)
                                                   (reset! ratio (- 1 r)))))))])]))

(def controls
  (horizontal-panel :items [(button :id :polyButt
                                    :text "Set Polygon"
                                    :listen [:action (fn [e]
                                                       (let [n (count @points)]
                                                         (if (> n 1)
                                                           (do 
                                                             (reset! mouseMode :startPoint)
                                                             (reset! numCorners n)))))])
                            (button :id :startButt :text "Next"
                                    :listen [:action (fn [e] (reset! mouseMode :addOne)
                                                       (nextPoint))])
                            (button :id :stopButt  :text "+100"
                                    :listen [:action (fn [e] (reset! mouseMode :progress)
                                                       (progress))])
                            (button :id :clearButt :text "Clear"
                                    :listen [:action (fn [e] (reset! mouseMode :polygon)
                                                       (clear))])]))


(def f (frame :title "Chaos Game"))

(config! f :content
         (border-panel :hgap 5 :vgap 5 :border 5
                       :center cnv
                       :south  (left-right-split controls ratioEntry
                                                 :divider-location 2/3)))

(def g2d (first (select f [:.graphics2d])))

                 
(listen cnv :mouse-clicked (fn [e]
                             (let [x (.getX e)
                                   y (.getY e)
                                   c (case @mouseMode
                                       :polygon "#33f"
                                       :startPoint "#f22"
                                       :addOne "#fff"
                                       :progress "#fff")]
                               (swap! points conj [x y c])
                               (repaint! cnv)
                               )))

(defn -main [& args]
  (-> f pack! show!))

