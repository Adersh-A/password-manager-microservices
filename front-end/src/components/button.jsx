const IconButton = ({ icon, text, onClick, className = '' }) => {
  return (
    <button
      onClick={onClick}
      className={`group inline-flex items-center gap-3 px-5 py-2.5 rounded-2xl border border-gray-300 bg-white shadow-md transition-all duration-200 hover:shadow-lg hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-blue-500 ${className}`}
    >
      <span className="transition-transform duration-200 group-hover:scale-110">
        {icon}
      </span>
      <span className="text-sm font-semibold text-gray-700">{text}</span>
    </button>
  );
};

export default IconButton;
