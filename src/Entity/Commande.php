<?php
namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use App\Entity\Client;
use App\Entity\Burger;
use App\Entity\Menu;
use App\Entity\Complement;
use App\Entity\Zone;
use App\Entity\Livreur;

#[ORM\Entity]
#[ORM\Table(name:"Commandes")]
class Commande
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\ManyToOne(targetEntity: Client::class, inversedBy: "commandes")]
    #[ORM\JoinColumn(nullable: false)]
    private Client $client;
    #[ORM\Column(type: "boolean")]
    private bool $paye = false;
    #[ORM\ManyToMany(targetEntity: Burger::class)]
    private Collection $burgers;

    #[ORM\ManyToMany(targetEntity: Menu::class)]
    private Collection $menus;

    #[ORM\ManyToMany(targetEntity: Complement::class)]
    private Collection $complements;

    #[ORM\ManyToOne(targetEntity: Zone::class)]
    private ?Zone $zone = null;

    #[ORM\ManyToOne(targetEntity: Livreur::class)]
    private ?Livreur $livreur = null;

    #[ORM\Column(type:"string", length:50)]
    private string $etat = 'En cours'; // En cours, Terminé, Annulé

    #[ORM\Column(type:"string", length:50)]
    private string $typeLivraison; // Sur place, À emporter, Livraison

    #[ORM\Column(type:"float")]
    private float $total = 0;

    #[ORM\Column(type:"datetime_immutable")]
    private \DateTimeImmutable  $dateCommande;

    public function __construct()
    {
        $this->burgers = new ArrayCollection();
        $this->menus = new ArrayCollection();
        $this->complements = new ArrayCollection();
        $this->dateCommande = new \DateTimeImmutable();
    }

    // getters et setters
    public function getId(): ?int { return $this->id; }
    public function getClient(): Client { return $this->client; }
    public function setClient(Client $client): self { $this->client = $client; return $this; }
    public function getBurgers(): Collection { return $this->burgers; }
    public function getMenus(): Collection { return $this->menus; }
    public function getComplements(): Collection { return $this->complements; }
    public function getZone(): ?Zone { return $this->zone; }
    public function setZone(?Zone $zone): self { $this->zone = $zone; return $this; }
    public function getLivreur(): ?Livreur { return $this->livreur; }
    public function setLivreur(?Livreur $livreur): self { $this->livreur = $livreur; return $this; }
    public function getEtat(): string { return $this->etat; }
    public function setEtat(string $etat): self { $this->etat = $etat; return $this; }
    public function getTypeLivraison(): string { return $this->typeLivraison; }
    public function setTypeLivraison(string $typeLivraison): self { $this->typeLivraison = $typeLivraison; return $this; }
    public function getTotal(): float { return $this->total; }
    public function setTotal(float $total): self { $this->total = $total; return $this; }
    public function getDate(): \DateTimeInterface { return $this->date; }
    public function isPaye(): bool
{
    return $this->paye;
}
    public function setPaye(bool $paye): self
    {
        $this->paye = $paye;
        return $this;
    }
}
