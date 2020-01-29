(ns {{namespace}}.views.bootstrap
  (:require
   [reagent.core :as r]
   ["reactstrap" :as rs]
   ["@fortawesome/react-fontawesome"
    :default FontAwesomeIcon]))

(def reactstrap-tags
  '[Alert
    Badge
    Breadcrumb
    BreadcrumbItem
    Button
    ButtonDropdown
    ButtonGroup
    ButtonToolbar
    Card
    CardBlock
    CardBody
    CardColumns
    CardDeck
    CardFooter
    CardGroup
    CardHeader
    CardImg
    CardImgOverlay
    CardLink
    CardSubtitle
    CardText
    CardTitle
    Carousel
    CarouselCaption
    CarouselControl
    CarouselIndicators
    CarouselItem
    Col
    Collapse
    Container
    CustomInput
    Dropdown
    DropdownItem
    DropdownMenu
    DropdownToggle
    Fade
    Form
    FormFeedback
    FormGroup
    FormText
    Input
    InputGroup
    InputGroupAddon
    InputGroupButton
    InputGroupButtonDropdown
    InputGroupText
    Jumbotron
    Label
    ListGroup
    ListGroupItem
    ListGroupItemHeading
    ListGroupItemText
    Media
    Modal
    ModalBody
    ModalFooter
    ModalHeader
    Nav
    NavDropdown
    NavItem
    NavLink
    Navbar
    NavbarBrand
    NavbarToggler
    Pagination
    PaginationItem
    PaginationLink
    Popover
    PopoverBody
    PopoverContent
    PopoverHeader
    PopoverTitle
    PopperContent
    PopperTargetHelper
    Progress
    Row
    TabContent
    TabPane
    Table
    Tooltip
    UncontrolledAlert
    UncontrolledButtonDropdown
    UncontrolledCarousel
    UncontrolledDropdown
    UncontrolledNavDropdown
    UncontrolledTooltip])


(defn componentize [component]
  (try
    (r/adapt-react-class component)
    (catch js/Object e
      nil)))


(defn cc [id]
  (get {:container      (componentize rs/Container)
        :button         (componentize rs/Button)
        :nav            (componentize rs/Nav)
        :navbar         (componentize rs/Navbar)
        :navbar-brand   (componentize rs/NavbarBrand)
        :navlink        (componentize rs/NavLink)
        :nav-item       (componentize rs/NavItem)
        :navbar-toggler (componentize rs/NavbarToggler)
        :collapse       (componentize rs/Collapse)}
       id))

(def button (componentize rs/Button))
(def button-group (componentize rs/ButtonGroup))

(def nav (componentize rs/Nav))

(def navbar (componentize rs/Navbar))

(def nav-link (componentize rs/NavLink))

(def nav-dropdown (componentize rs/NavDropdown))

(def navbar-toggler (componentize rs/NavbarToggler))

(def navbar-brand (componentize rs/NavbarBrand))

(def navbar-text (componentize rs/NavbarText))

(def nav-item (componentize rs/NavItem))

(def collapse (componentize rs/Collapse))

(def uncontrolled-dropdown (componentize rs/UncontrolledDropdown))
(def dropdown-toggle (componentize rs/DropdownToggle))

(def dropdown-menu (componentize rs/DropdownMenu))
(def dropdown-item (componentize rs/DropdownItem))

(def form (componentize rs/Form))
(def form-group (componentize rs/FormGroup))

(def label (componentize rs/Label))
(def input (componentize rs/Input))
(def table (componentize rs/Table))
(def container (componentize rs/Container))

(def col (componentize rs/Col))
(def row (componentize rs/Row))
(def fade (componentize rs/Fade))
(def input-group-addon (componentize rs/InputGroupAddon))

(def input-group (componentize rs/InputGroup))

(def alert (componentize rs/Alert))

(def fieldset (componentize rs/Fieldset))

(def pagination (componentize rs/Pagination))
(def pagination-item (componentize rs/PaginationItem))
(def pagination-link (componentize rs/PaginationLink))

(def tab-content (componentize rs/TabContent))
(def tab-pane (componentize rs/TabPane))

(def button-dropdown (componentize rs/ButtonDropdown))

(def card (componentize rs/Card))
(def card-title (componentize rs/CardTitle))
(def card-text (componentize rs/CardText))
(def card-header (componentize rs/CardHeader))
(def card-link (componentize rs/CardLink))

(def class
  {:label "col-sm-2 col-form-label"
   :form  "form-control-lg"
   :date  "col-sm-7 form-control-lg"})

(def font-awesome-icon FontAwesomeIcon)
