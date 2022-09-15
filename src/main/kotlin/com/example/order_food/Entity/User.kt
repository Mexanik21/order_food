package com.example.order_food.Entity

import com.example.order_food.enums.Language
import com.example.order_food.enums.Role
import com.example.order_food.enums.Step
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity(name = "users")
class User(
    @Column(length = 48, unique = true)
    var telegramId: String,
    @Enumerated(EnumType.STRING)
    var role: Role? = Role.USER,
    @Enumerated(EnumType.STRING)
    var step: Step? = Step.START,
    @Enumerated(EnumType.STRING)
    var lang: Language? = Language.UZ,
    @Column(length = 24, unique = true)
    private var username: String? = null,
    @Column(length = 128)
    var fullName: String? = null,
    @Column(length = 16)
    var phoneNumber: String? = null,
    var cache:String? = null,



    private var password: String? = null,
    val isEnabled: Boolean? = true,
    val isCredentialsNonExpired: Boolean? = true,
    val isAccountNonExpired: Boolean? = true,
    val isAccountNonLocked: Boolean? = true,


    ) : BaseEntity(),UserDetails {
    override fun getUsername(): String = username!!
    fun setUsername(username: String){
        this.username = username
    }
    override fun getPassword(): String = password!!

    fun setPassword(password: String){
        this.password = password
    }
    override fun isEnabled(): Boolean = isEnabled!!
    override fun isCredentialsNonExpired(): Boolean = isCredentialsNonExpired!!
    override fun isAccountNonExpired(): Boolean = isAccountNonExpired!!
    override fun isAccountNonLocked(): Boolean = isAccountNonLocked!!
    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {

        val grantedAuthorities: MutableSet<GrantedAuthority> = HashSet()
        grantedAuthorities.add(SimpleGrantedAuthority(role!!.name))
        return grantedAuthorities
    }
}