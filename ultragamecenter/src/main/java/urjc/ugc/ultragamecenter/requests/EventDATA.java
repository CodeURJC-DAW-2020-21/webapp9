package urjc.ugc.ultragamecenter.requests;

import urjc.ugc.ultragamecenter.models.Event;

public class EventDATA {
        private Integer total;
        
        private Integer restante;

    
        public Integer getTotal() {
            return total;
        }

        public EventDATA(Integer total, Integer restante, Integer disponible) {
            this.total = total;
            this.restante = restante;
            this.disponible = disponible;
        }

        public EventDATA(Event e){
            this.total=e.getCapacity();
            this.restante=e.getlikes();
            this.disponible=e.getCapacity()-e.getlikes();
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public Integer getRestante() {
            return restante;
        }

        public void setRestante(Integer restante) {
            this.restante = restante;
        }

        public Integer getDisponible() {
            return disponible;
        }

        public void setDisponible(Integer disponible) {
            this.disponible = disponible;
        }

        private Integer disponible;
}
