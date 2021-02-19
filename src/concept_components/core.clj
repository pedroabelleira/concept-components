(ns concept-components.core)

;; Emulación del useState de React. No es necesario en
;; Reagent, pero hace las cosas más fáciles de entender
(defn use-state[initial-val & [validator]]
  (let [state (atom initial-val)]
    [(fn [] @state)
     (fn [new-val]
       (if validator
         (validator new-val)
         (reset! state new-val)))]))

;; "Base de datos" de mensajes
(def message-db
  {[:welcome :en] "Welcome!"
   [:welcome :fr] "Bienvenu(e)!"})

;; Componente que muestra un mensaje con clave key en el idioma lang
(defn message [key lang]
(or (get message-db [key lang])
    "*not found*"))

(defn language-validator [lang]
(or contains? supported-languages lang
    (println (str lang) ": not supported language")))

(defn top-bar [logo welcome-msg menu]
[:div [:div logo] [:div welcome-msg] [:div {:align :right} menu]])

(defn main-content [title text] [:h1 title] [:div text])

(defn footer [] [:div {:class :footer} "*test footer*"])

(defn app [top-bar main-content] [:div top-bar] [:div main-content])

;; Imaginary react rendering function.
(defn react-render [cmp] (println (str cmp)))

;; Montamos la aplicación
(let [supported-languages       #{:en :fr}
      [curr-lang set-curr-lang] (use-state :en language-validator)
      message                   #(message % (curr-lang))
      welcome-msg               (message :welcome)
      top-bar                   (top-bar "*logo*" welcome-msg "*menu*")
      main-content              (main-content welcome-msg "*main-text*")
      app                       (app top-bar main-content)]
  (react-render app))
