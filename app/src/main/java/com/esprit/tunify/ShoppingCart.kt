package com.esprit.tunify

import android.content.Context
import com.esprit.tunify.model.CartItem
import io.paperdb.Paper

class ShoppingCart {

    companion object {
        fun addItem(cartItem: CartItem) {
            val cart = ShoppingCart.getCart()

            val targetItem = cart.singleOrNull { it.product._id == cartItem.product._id }
            if (targetItem == null) {
                cartItem.quantity++
                cart.add(cartItem)
            } else {
                targetItem.quantity++
            }
            ShoppingCart.saveCart(cart)
        }

        fun removeItem(cartItem: CartItem, context: Context) {
            val cart = ShoppingCart.getCart()

            val targetItem = cart.singleOrNull { it.product._id == cartItem.product._id }
            if (targetItem != null) {
                if (targetItem.quantity > 0) {
                    targetItem.quantity--
                } else {
                    cart.remove(targetItem)
                }
            }

            ShoppingCart.saveCart(cart)
        }

        fun saveCart(cart: MutableList<CartItem>) {
            Paper.book().write("cart", cart)
        }

        fun getCart(): MutableList<CartItem> {
            return Paper.book().read("cart", mutableListOf())!!
        }

        fun getShoppingCartSize(): Int {
            var cartSize = 0
            ShoppingCart.getCart().forEach {
                cartSize += it.quantity;
            }

            return cartSize
        }
    }

}