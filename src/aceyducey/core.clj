(ns aceyducey.core
  (:gen-class))

; game is:
; [X] start with deck 1 - 13
; [X] draw 2 cards.
; [X] show user these cards.
;     [X] if 1 show Ace
;     [X] if 11 show Jack
;     [X] if 12 show Queen
;     [X] if 13 show King
; [X] ask if they want to bet,
; [X] get their bet,
; [ ] check bet <= bankroll
; [ ] if bet ok
; [ ] draw random card
; [X] if card between the 2 existing cards.
; [ ] player wins their bet
; [ ] increment bankroll
; [ ] if player loses, decrement their pot.
; [ ] end when pot reaches 0
; [ ] if amount bet is 0 print chicken and go again.

; draw a random card from the deck, and return the card and the deck minus that card.
(defn draw-random-card
  [cards]
  (let [card (rand-nth cards)
        index (.indexOf cards card)]
    {:drawn-card card :remaining-cards (vec (concat (subvec cards 0 index)
                                                       (subvec cards (inc index))))}))

(defn winning-play?
  [first-card second-card player-card]
  (or (< first-card player-card second-card)
      (> first-card player-card second-card)))

(defn play-game
  [bank-roll]
  (let [full-deck ["Ace" 2 3 4 5 6 7 8 9 10 "Jack" "Queen" "King"]
        first-draw (draw-random-card full-deck)
        first-card (:drawn-card first-draw)
        second-draw (draw-random-card (:remaining-cards first-draw))
        second-card (:drawn-card second-draw)
        remaining-deck (:remaining-cards second-draw)]
    (println "First card: " first-card)
    (println "Second card: " second-card)
    (println "How much do you want to bet [Bankroll: " bank-roll "] ?")
    (let [bet (read-line)]
      (when (= 0 bet)
         (println "Chicken!")
         (recur bank-roll)))))

(defn can-afford-bet
  [bet bankroll]
  (<= bet bankroll))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!")
  (play-game 100))

(let [x 5]
  (println "x is " 5)
  (let [y (read-line)]
    (= x y
       (println "y is " y)
       (println "x = y"))))