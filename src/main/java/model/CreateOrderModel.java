package model;

import java.util.List;

public class CreateOrderModel {

        private List<String> ingredients;

        public CreateOrderModel() {
        }

        public CreateOrderModel(List<String> ingredients) {
            this.ingredients = ingredients;
        }

        public List<String> getIngredients() {
            return ingredients;
        }

        public void setIngredients(List<String> ingredients) {
            this.ingredients = ingredients;
        }
    }
