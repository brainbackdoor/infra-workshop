package members.domain

import domain.RandomId
import jakarta.persistence.Entity

@Entity
class Member() : RandomId<Member>() {
}