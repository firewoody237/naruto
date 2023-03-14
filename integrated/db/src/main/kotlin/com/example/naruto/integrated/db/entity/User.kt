package com.example.naruto.integrated.db.entity

import com.example.naruto.integrated.db.enum.Authority
import com.example.naruto.integrated.db.enum.Grade
import javax.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@EntityListeners(value = [AuditingEntityListener::class])
@Table(name = "user")
data class User(
    @Id
    @GeneratedValue
    val id: Long = 0L,
    @Column(nullable = false, length = 20, unique = true)
    var name: String? = "",
    @Column(nullable = false, length = 20)
    var nickname: String? = "",
    @Column(nullable = false)
    var email: String? = "",
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var grade: Grade = Grade.GREEN,
    @Column
    var point: Long = 0,
    @Column
    var authority: Authority = Authority.NORMAL,
): BaseTime(), java.io.Serializable {

    @Column
    @Convert(converter = PasswordConverter::class)
    var password: String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "User(id=$id, name='$name', nickname='$nickname', password='$password', email='$email', grade='$grade', point='$point', authority='$authority', createdAt='$createdAt', modifiedAt='$modifiedAt')"
    }
}