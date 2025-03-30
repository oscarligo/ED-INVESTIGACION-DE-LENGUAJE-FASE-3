;; ejecutar con: clojure -M -m Blender

(ns Blender)

;; licuadora, con velocidad y estado (llena o vacía)
(defn create-blender []
  (atom {:speed 0 :full? false}))

;; Función para aumentar la velocidad, solo si está llena
(defn increase-speed [blender]
  (swap! blender
         (fn [{:keys [speed full?] :as state}]
           (if (and full? (< speed 10))
             (assoc state :speed (inc speed))
             (do
               (println (if full? "\n* Velocidad máxima alcanzada." "\n* No se pueda aumentar la velocidad al estar vacía."))
               state)))))

;; Función para disminuir la velocidad
(defn decrease-speed [blender]
  (swap! blender update :speed #(max 0 (dec %))))

;; Función para llenar la licuadora
(defn fill-blender [blender]
  (swap! blender assoc :full? true)
  (println "\n* Licuadora llena."))

;; Función para vaciar la licuadora (se apaga al vaciarse)
(defn empty-blender [blender]
  (swap! blender assoc :full? false :speed 0)
  (println "\n* Licuadora vaciada y apagada."))

;; Función para obtener el estado actual
(defn get-status [blender]
  (let [{:keys [speed full?]} @blender]
    (println (str "Velocidad actual: " speed ", Estado: " (if full? "Llena" "Vacía")))))

;; Función para mostrar el menú y procesar opciones
(defn show-menu []
  (println "\nSumulación de una Licuadora\n")
  (println "1. Aumentar velocidad")
  (println "2. Disminuir velocidad")
  (println "3. Llenar licuadora")
  (println "4. Vaciar licuadora")
  (println "5. Consultar estado")
  (println "6. Salir"))

;; Función para procesar la opción seleccionada
(defn process-option [option blender]
  (case option
    1 (increase-speed blender)
    2 (decrease-speed blender)
    3 (fill-blender blender)
    4 (empty-blender blender)
    5 (get-status blender)
    6 (do (println "\n* Fin de la simulación") (System/exit 0))
    (println "\nOpción no válida.")))

(defn -main []
  (let [blender (create-blender)]
    (loop []
      (show-menu)
      (let [option (read-line)]
        (process-option (Integer/parseInt option) blender)
        (recur)))))
      
