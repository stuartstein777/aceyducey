(ns aceyducey.core
  (:gen-class))

(defn draw-random-card
  [cards]
  (let [card (rand-nth cards)]
    {:drawn-card (peek cards) :remaining-cards (vec (pop cards))}))
;
(defn winning-play?
  [first-card second-card player-card]
  (or (< first-card player-card second-card)
      (> first-card player-card second-card)))

(defn get-bet
  [bank-roll]
  (let [bet (-> (read-line)
                (Integer/parseInt))]
    (if (> bet bank-roll)
      (do
        (println "You can't afford that bet")
        (recur bank-roll))
      bet)))

(defn display-card
  [card]
  (case card
    1 "Ace"
    11 "Jack"
    12 "Queen"
    13 "King"
    (2 3 4 5 6 7 8 9 10) (str card)))

(defn play-game
  [bank-roll]
  (if (<= bank-roll 0)
    (println "Busted! Game over.")
    (let [full-deck (shuffle [1 2 3 4 5 6 7 8 9 10 11 12 13])
          first-draw (draw-random-card full-deck)
          first-card (:drawn-card first-draw)
          second-draw (draw-random-card (:remaining-cards first-draw))
          second-card (:drawn-card second-draw)
          remaining-deck (:remaining-cards second-draw)]
      (println "First card: " (display-card first-card))
      (println "Second card: " (display-card second-card))
      (println "How much do you want to bet [Bankroll: " bank-roll "] ?")
      (let [bet (get-bet bank-roll)]
        (if (= 0 bet)
          (do
            (println "Chicken!")
            (recur bank-roll))
          (let [player-card (:drawn-card (draw-random-card remaining-deck))]
            (do
              (println "Player card: " (display-card player-card))
              (if (winning-play? first-card second-card player-card)
                (do
                  (println "Win!")
                  (recur (+ bank-roll bet)))
                (do
                  (println "Lose!")
                  (recur (- bank-roll bet)))))))))))

(defn get-bet
  [bank-roll]
  (let [bet (-> (read-line)
                (Integer/parseInt))]
    (if (> bet bank-roll)
      (do
        (println "You can't afford that bet.")
        (println "How much do you want to bet [Bankroll: " bank-roll "] ?")
        (recur bank-roll))
      bet)))

(defn -main
  [& args]
  (play-game 100))