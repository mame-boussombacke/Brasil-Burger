<?php
namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity]
#[ORM\Table(name:"Clients")]
class Client
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length:100)]
    private string $nom;

    #[ORM\Column(length:100)]
    private string $prenom;

    #[ORM\Column(length:50)]
    private string $telephone;

    #[ORM\Column(length:255, unique:true)]
    private string $email;

    #[ORM\Column(type:"json")]
    private array $roles = ['ROLE_CLIENT'];
    #[ORM\OneToMany(mappedBy: "client", targetEntity: Commande::class)]
    private Collection $commandes;

    // getters et setters
    public function getId(): ?int { return $this->id; }
    public function getNom(): string { return $this->nom; }
    public function setNom(string $nom): self { $this->nom = $nom; return $this; }
    public function getPrenom(): string { return $this->prenom; }
    public function setPrenom(string $prenom): self { $this->prenom = $prenom; return $this; }
    public function getTelephone(): string { return $this->telephone; }
    public function setTelephone(string $telephone): self { $this->telephone = $telephone; return $this; }
    public function getEmail(): string { return $this->email; }
    public function setEmail(string $email): self { $this->email = $email; return $this; }
    public function getRoles(): array { return $this->roles; }
    public function setRoles(array $roles): self { $this->roles = $roles; return $this; }
}
