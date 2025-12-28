<?php
namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use App\Entity\Burger;

#[ORM\Entity]
#[ORM\Table(name:"Menus")]
class Menu
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(name:"Nom", type:"string", length:255)]
    private string $nom;

    #[ORM\Column(name:"ImageUrl", type:"string", length:255, nullable:true)]
    private ?string $imageUrl = null;

    #[ORM\Column(name:"EstDisponible", type:"boolean")]
    private bool $estDisponible = true;

    #[ORM\ManyToMany(targetEntity: Burger::class)]
    #[ORM\JoinTable(name:"menu_burgers",
        joinColumns:[new ORM\JoinColumn(name:"menu_id", referencedColumnName:"id")],
        inverseJoinColumns:[new ORM\JoinColumn(name:"burger_id", referencedColumnName:"id")]
    )]
    private Collection $burgers;

    public function __construct() { $this->burgers = new ArrayCollection(); }

    // getters et setters
    public function getId(): ?int { return $this->id; }
    public function getNom(): string { return $this->nom; }
    public function setNom(string $nom): self { $this->nom = $nom; return $this; }
    public function getImageUrl(): ?string { return $this->imageUrl; }
    public function setImageUrl(?string $imageUrl): self { $this->imageUrl = $imageUrl; return $this; }
    public function getEstDisponible(): bool { return $this->estDisponible; }
    public function setEstDisponible(bool $estDisponible): self { $this->estDisponible = $estDisponible; return $this; }
    public function getBurgers(): Collection { return $this->burgers; }
    public function addBurger(Burger $burger): self { if (!$this->burgers->contains($burger)) $this->burgers->add($burger); return $this; }
    public function removeBurger(Burger $burger): self { $this->burgers->removeElement($burger); return $this; }
}
